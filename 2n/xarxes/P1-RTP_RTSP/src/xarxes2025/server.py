from loguru import logger
import socket, os.path, threading, time
from xarxes2025.udpdatagram import UDPDatagram
from xarxes2025.videoprocessor import VideoProcessor
from xarxes2025.state_machine import StateMachine
from xarxes2025.client_data import ClientData
from random import randint

class Server(object):
    def __init__(self, port, host):       
        """
        Initialize a new VideoStreaming server.

        :param port: The port to listen on.
        :param host: The host address to bind the server to.
        """
        self.port = port
        self.host = host
        logger.debug(f"Server created ")

        # Create and bind the RTSP socket
        self.rtsp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.rtsp_socket.bind((self.host, self.port))
        self.rtsp_socket.listen()
        logger.debug(f"Server listening on {self.host}:{self.port}")

        # Accept incoming client connections in a loop
        while True:
            sock, addr = self.rtsp_socket.accept()
            client = ClientData(sock, addr)
            # Create a thread to handle the client
            client_thread = threading.Thread(
                target=self.handle_client,
                args=(client,)
            )
            client_thread.daemon = True
            client_thread.start()

    def play_video(self, client: ClientData):
        """
        Play the video by sending UDP frames to the client.

        :param client: The client to send video frames to.
        """
        logger.debug("Playing video")
        while client.is_playing():
            self.send_udp_frame(client)
            time.sleep(1/25)  # Send frames at 25 FPS
    
    def handle_client(self, client: ClientData):
        """
        Handle communication with a connected client.

        :param client: The client to handle.
        """
        try:
            while True:
                # Wait for a request from the client
                request = client.rtsp_socket.recv(1024).decode()
                logger.debug(f"Received request from {client.address}: {request}")
                
                if not request:
                    logger.info(f"Client {client.address} disconnected")
                    break
                
                # Process the client's request
                logger.debug(f"Handling request from {client.address}")
                self.handle_request(request, client)
                
        except Exception as e:
            logger.error(f"Error handling client {client.address}: {e}")

    def handle_request(self, request, client: ClientData):
        """
        Handle a request from the client.

        :param request: The request from the client.
        :param client: The client sending the request.
        """
        if "SETUP" in request:
            if client.state_machine.transition("SETUP"):
                logger.debug(f"State machine from {client.address} transition to SETUP")
                self.handle_setup(request, client)
        elif "PLAY" in request:
            if client.state_machine.transition("PLAY"):
                logger.debug(f"State machine from {client.address} transition to PLAY")
                self.handle_play(request, client)
        elif "PAUSE" in request:
            if client.state_machine.transition("PAUSE"):
                logger.debug(f"State machine from {client.address} transition to PAUSE")
                self.handle_pause(request, client)
        elif "TEARDOWN" in request:
            if client.state_machine.transition("TEARDOWN"):
                logger.debug(f"State machine from {client.address} transition to TEARDOWN")
                self.handle_teardown(request, client)
        else:
            logger.error(f"Unknown request: {request}") 
            # Send a 400 Bad Request response
            client.rtsp_socket.sendall(b"RTSP/1.0 400 Bad Request\r\n")
    
    def handle_setup(self, request, client: ClientData):
        """
        Handle a SETUP request from the client.

        :param request: The SETUP request from the client.
        :param client: The client sending the request.
        """
        logger.debug("Handling SETUP request")
        request_lines = request.splitlines()
        filename = request_lines[0].split(" ")[1]
        logger.debug(f"Filename: {filename}")
        # Check if the requested file exists
        if not os.path.exists(filename):
            logger.error(f"File {filename} not found")
            client.rtsp_socket.sendall(f"RTSP/1.0 404 File Not Found\r\n".encode())
            return
        
        # Initialize the VideoProcessor for the requested file
        client.video = VideoProcessor(filename)
        logger.debug(f"VideoProcessor created for file {filename} and client {client.address}")
        
        cseq = int(request_lines[1].split(":")[1].strip())
        logger.debug(f"CSeq: {cseq}")
        
        # Extract the client's UDP port
        client.udp_port = int(request_lines[2].split("=")[1].strip())
        logger.debug(f"Client UDP port: {client.udp_port}")

        # Create the RTP socket
        client.rtp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        client.rtp_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        
        def create_session_id():
            """
            Create a new session ID.
            """
            random_num = randint(1, 9999999999)
            return f"XARXES_{random_num:010d}"
        
        # Generate a session ID and send the response
        session_id = create_session_id()
        logger.debug(f"Session ID: {session_id} for client {client.address}")
        client.rtsp_socket.sendall(f"RTSP/1.0 200 OK\r\nCSeq: {cseq}\r\nSession: {session_id}\r\n".encode())

    def handle_play(self, request, client: ClientData):
        """
        Handle a PLAY request from the client.

        :param request: The PLAY request from the client.
        :param client: The client sending the request.
        """
        logger.debug("Handling PLAY request")
        request_lines = request.splitlines()
        cseq = int(request_lines[1].split(":")[1].strip())
        logger.debug(f"CSeq: {cseq}")
        session_id = request_lines[2].split(":")[1].strip()
        logger.debug(f"Session ID: {session_id}")
        
        # Send a 200 OK response
        client.rtsp_socket.sendall(f"RTSP/1.0 200 OK\r\nCSeq: {cseq}\r\nSession: {session_id}\r\n".encode())
        client.playing = True
        logger.debug("Started playing video")
        
        # Create a thread to play the video
        client.playing_thread = threading.Thread(target=self.play_video, args=(client,))
        client.playing_thread.daemon = True
        client.playing_thread.start()
    
    def handle_pause(self, request, client: ClientData):
        """
        Handle a PAUSE request from the client.

        :param request: The PAUSE request from the client.
        :param client: The client sending the request.
        """
        logger.debug("Handling PAUSE request")
        request_lines = request.splitlines()
        cseq = int(request_lines[1].split(":")[1].strip())
        logger.debug(f"CSeq: {cseq}")
        session_id = request_lines[2].split(":")[1].strip()
        logger.debug(f"Session ID: {session_id}")
        
        # Send a 200 OK response and stop playback
        client.rtsp_socket.sendall(f"RTSP/1.0 200 OK\r\nCSeq: {cseq}\r\nSession: {session_id}\r\n".encode())
        client.playing = False
    
    def handle_teardown(self, request, client: ClientData):
        """
        Handle a TEARDOWN request from the client.

        :param request: The TEARDOWN request from the client.
        :param client: The client sending the request.
        """
        logger.debug("Handling TEARDOWN request")
        request_lines = request.splitlines()
        cseq = int(request_lines[1].split(":")[1].strip())
        logger.debug(f"CSeq: {cseq}")
        session_id = request_lines[2].split(":")[1].strip()
        logger.debug(f"Session ID: {session_id}")
        
        # Send a 200 OK response and clean up resources
        client.rtsp_socket.sendall(f"RTSP/1.0 200 OK\r\nCSeq: {cseq}\r\nSession: {session_id}\r\n".encode())
        
        client.playing = False
        # Close the RTP socket
        client.rtp_socket.close()

        if hasattr(client, 'rtp_socket'):
            try:
                client.rtp_socket.close()
                logger.debug("RTP socket closed")
            except Exception as e:
                logger.error(f"Error closing RTP socket: {e}")

        # Close the VideoProcessor properly if it exists
        if hasattr(client, 'video'):
            try:
                client.video.cap.release()
                logger.debug("Video processor released")
            except Exception as e:
                logger.error(f"Error releasing video processor: {e}")
        
    def send_udp_frame(self, client: ClientData):
        """
        Send a single video frame to the client via UDP.

        :param client: The client to send the frame to.
        """
        try:
            # Get the next video frame
            data = client.video.next_frame()
            if data:
                if len(data) > 0:
                    # Create a UDP datagram
                    udp_datagram = UDPDatagram(client.frame_number, data).get_datagram()
                    # Send the datagram to the client
                    client.rtp_socket.sendto(udp_datagram, (client.host, client.udp_port))
                    logger.trace(f"Sending frame {client.frame_number}")
                    client.frame_number += 1
        except Exception as e:
            logger.error(f"Error in send_udp_frame: {e}")
            client.playing = False  # Stop playback on error

