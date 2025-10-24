import sys 
from tkinter import Tk, Label, Button, W, E, N, S
from tkinter import messagebox
import tkinter as tk
import socket, threading, time
from xarxes2025.state_machine import StateMachine
from xarxes2025.udpdatagram import UDPDatagram
from loguru import logger
from PIL import Image, ImageTk
import io

class Client(object):
    def __init__(self, server_port, filename, udp_port, host):
        """
        Initialize the client.

        :param server_port: The port of the server to connect to.
        :param filename: The name of the video file to request.
        :param udp_port: The UDP port for receiving RTP packets.
        :param host: The server's host address.
        """
        logger.debug(f"Client created ")
        self.server_port = server_port
        self.filename = filename
        self.udp_port = udp_port
        self.host = host
        self.cseq = 0  # Sequence number for RTSP requests
        self.state_machine = StateMachine()  # State machine to manage RTSP states
        self.rtsp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.rtsp_socket.connect((self.host, self.server_port))  # Connect to the server
        logger.debug(f"Connected to server {self.host}:{self.server_port}")
        self.create_ui()  # Create the user interface

    def create_ui(self):
        """
        Create the user interface for the client.

        This function creates the window for the client and its
        buttons and labels. It also sets up the window to call the
        close window function when the window is closed.
        """
        self.root = Tk()
        self.root.wm_title("RTP Client")  # Set the window title
        self.root.protocol("WM_DELETE_WINDOW", self.ui_close_window)  # Handle window close event

        # Create buttons for RTSP operations
        self.setup = self._create_button("Setup", self.ui_setup_event, 0, 0)
        self.start = self._create_button("Play", self.ui_play_event, 0, 1)
        self.pause = self._create_button("Pause", self.ui_pause_event, 0, 2)
        self.teardown = self._create_button("Teardown", self.ui_teardown_event, 0, 3)

        # Create a label to display the video
        self.movie = Label(self.root, height=29)
        self.movie.grid(row=1, column=0, columnspan=4, sticky=W+E+N+S, padx=5, pady=5)

        # Create a label to display text messages
        self.text = Label(self.root, height=3)
        self.text.grid(row=2, column=0, columnspan=4, sticky=W+E+N+S, padx=5, pady=5)

        return self.root

    def _create_button(self, text, command, row=0, column=0, width=20, padx=3, pady=3):
        """
        Create a button widget with the given text, command, and layout options.

        :param text: The text to display on the button.
        :param command: The function to call when the button is clicked.
        :param row: The row number of the button in the grid.
        :param column: The column number of the button in the grid.
        :param width: The width of the button.
        :param padx: The horizontal padding of the button.
        :param pady: The vertical padding of the button.
        :return: The button widget.
        """
        button = Button(self.root, width=width, padx=padx, pady=pady)
        button["text"] = text
        button["command"] = command
        button.grid(row=row, column=column, padx=2, pady=2)
        return button

    def ui_close_window(self):
        """
        Handle the window close event.

        This function stops any ongoing playback, closes sockets, and destroys the GUI window.
        """
        logger.debug("Closing window...")

        # Stop playback if it is ongoing
        if hasattr(self, 'playing'):
            self.playing = False

        # Close the RTP socket if it exists
        if hasattr(self, 'rtp_socket'):
            try:
                self.rtp_socket.close()
                logger.debug("RTP socket closed")
            except Exception as e:
                logger.error(f"Error closing RTP socket: {e}")

        # Join the playback thread if it is running
        if hasattr(self, 'playing_thread') and self.playing_thread.is_alive():
            try:
                self.playing_thread.join(timeout=2.0)
                if self.playing_thread.is_alive():
                    logger.warning("Playing thread did not terminate properly")
            except Exception as e:
                logger.error(f"Error joining playing thread: {e}")

        # Close the RTSP socket
        if hasattr(self, 'rtsp_socket'):
            try:
                self.rtsp_socket.close()
                logger.debug("RTSP socket closed")
            except Exception as e:
                logger.error(f"Error closing RTSP socket: {e}")

        # Destroy the GUI window
        self.root.destroy()
        logger.debug("Window closed")

    def ui_setup_event(self):
        """
        Handle the Setup button click event.

        This function sends a SETUP request to the server and prepares the client for playback.
        """
        logger.debug("Setup button clicked")
        self.text["text"] = "Setup button clicked"
        self.cseq += 1  # Increment the sequence number

        # Transition the state machine to SETUP
        if self.state_machine.transition("SETUP"):
            # Construct and send the SETUP RTSP request
            message = f"SETUP {self.filename} RTSP/1.0\r\nCSeq: {self.cseq}\r\nTransport: RTP/UDP; client_port= {self.udp_port}\r\n\r\n"
            self.rtsp_socket.send(message.encode())
            logger.debug(f"RTSP message sent: {message}")

            # Receive and process the server's response
            resposta = self.rtsp_socket.recv(1024).decode().splitlines()
            logger.debug(f"RTSP response received: {resposta}")

            if "200 OK" not in resposta[0]:
                logger.error(f"Error in RTSP response: {resposta}")
                return

            # Extract the session ID and prepare the RTP socket
            self.session_id = resposta[2].split(":")[1].strip()
            logger.debug(f"Session ID: {self.session_id}")
            self.rtp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
            self.rtp_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

            try:
                self.rtp_socket.bind((self.host, self.udp_port))
                logger.debug(f"RTP socket bound to {self.host}:{self.udp_port}")
            except OSError as e:
                logger.error(f"Could not bind to port {self.udp_port}: {e}")
            return
        else:
            logger.debug(f"Transition failed: Tried SETUP in state {self.state_machine.get_state()}")
            return

    def ui_play_event(self):
        """
        Handle the Play button click event.

        This function sends a PLAY request to the server and starts the playback thread.
        """
        logger.debug("Play button clicked")
        self.text["text"] = "Play button clicked"
        self.cseq += 1

        if self.state_machine.transition("PLAY"):
            # Construct and send the PLAY RTSP request
            message = f"PLAY {self.filename} RTSP/1.0\r\nCSeq: {self.cseq}\r\nSession: {self.session_id}\r\n\r\n"
            self.rtsp_socket.send(message.encode())
            logger.debug(f"RTSP message sent: {message}")
            
            # Receive and process the server's response
            resposta = self.rtsp_socket.recv(1024).decode().splitlines()
            logger.debug(f"RTSP response received:{resposta}")
            if "200 OK" not in resposta[0]:
                logger.error(f"Error in RTSP response: {resposta}")
                return
            
            # Start the playback thread
            self.playing_thread = threading.Thread(target=self.play_video)
            self.playing_thread.start()

        else:
            logger.debug(f"Transition failed: Tried PLAY in state {self.state_machine.get_state()}")
            return

    def play_video(self):
        """
        Play the video by receiving RTP packets and updating the GUI.
        """
        logger.debug("Playing video")
        self.text["text"] = "Playing video"
        self.playing = True

        # Set a timeout on the RTP socket to make it responsive to shutdown
        self.rtp_socket.settimeout(0.5)  # Timeout of 0.5 seconds

        while self.playing:
            try:
                data, _ = self.rtp_socket.recvfrom(65536)
                datagram = UDPDatagram(0, b'')
                datagram.decode(data)
                payload = datagram.get_payload()
                if payload:
                    self.updateMovie(payload)
                else:
                    logger.debug("Empty payload received, stopping playback")
                    break
            except socket.timeout:
                continue
            except (socket.error, IOError) as e:
                logger.debug(f"Socket error in play_video: {e}")
                break
            except Exception as e:
                logger.error(f"Unexpected error in play_video: {e}")
                break

        logger.debug("Exiting play_video thread")

    def ui_pause_event(self):
        """
        Handle the Pause button click event.

        This function sends a PAUSE request to the server and stops the playback.
        """
        logger.debug("Pause button clicked")
        self.text["text"] = "Pause button clicked"
        self.cseq += 1
        if self.state_machine.transition("PAUSE"):
            # Construct and send the PAUSE RTSP request
            message = f"PAUSE {self.filename} RTSP/1.0\r\nCSeq: {self.cseq}\r\nSession: {self.session_id}\r\n\r\n"
            self.rtsp_socket.send(message.encode())
            logger.debug(f"RTSP message sent: {message}")
            
            # Receive and process the server's response
            resposta = self.rtsp_socket.recv(4096).decode().splitlines()
            logger.debug(f"RTSP response received:{resposta}")
            if "200 OK" not in resposta[0]:
                logger.error(f"Error in RTSP response: {resposta}")
                return

            # Stop playback
            self.playing = False
        else:
            logger.debug(f"Transition failed: Tried PAUSE in state {self.state_machine.get_state()}")
            return

    def ui_teardown_event(self):
        """
        Handle the Teardown button click event.

        This function sends a TEARDOWN request to the server and closes the session.
        """
        logger.debug("Teardown button clicked")
        self.text["text"] = "Teardown button clicked"
        self.cseq += 1
        if self.state_machine.transition("TEARDOWN"):
            # Construct and send the TEARDOWN RTSP request
            message = f"TEARDOWN {self.filename} RTSP/1.0\r\nCSeq: {self.cseq}\r\nSession: {self.session_id}\r\n\r\n"
            self.rtsp_socket.send(message.encode())
            logger.debug(f"RTSP message sent: {message}")
            
            # Receive and process the server's response
            resposta = self.rtsp_socket.recv(4096).decode().splitlines()
            logger.debug(f"RTSP response received:{resposta}")
            if "200 OK" not in resposta[0]:
                logger.error(f"Error in RTSP response: {resposta}")
                return

            # Stop the thread and close the RTP socket
            self.playing = False
            if hasattr(self, 'rtp_socket'):
                try:
                    self.rtp_socket.close()
                    logger.debug("RTP socket closed")
                except Exception as e:
                    logger.error(f"Error closing RTP socket: {e}")

            # Join the thread
            if hasattr(self, 'playing_thread') and self.playing_thread.is_alive():
                try:
                    self.playing_thread.join(timeout=2.0)
                    if self.playing_thread.is_alive():
                        logger.warning("Playing thread did not terminate properly")
                except Exception as e:
                    logger.error(f"Error joining playing thread: {e}")

            self.session_id = None
            logger.debug("Session closed")
        else:
            logger.debug(f"Transition failed: Tried TEARDOWN in state {self.state_machine.get_state()}")
            return

    def updateMovie(self, data):
        """Update the video frame in the GUI from the byte buffer we received."""

        photo = ImageTk.PhotoImage(Image.open(io.BytesIO(data)))
        logger.trace(f"Image loaded from buffer")
        self.movie.configure(image = photo, height=380) 
        self.movie.photo_image = photo