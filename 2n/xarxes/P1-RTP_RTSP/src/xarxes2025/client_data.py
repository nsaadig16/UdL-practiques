from xarxes2025.state_machine import StateMachine
from xarxes2025.videoprocessor import VideoProcessor
import socket

class ClientData:
    def __init__(self, rstp_socket, address):
        """
        Initialize a new client data object.
        """
        self.state_machine : StateMachine = StateMachine()
        self.rtsp_socket : socket.socket = rstp_socket
        self.address = address
        self.host = address[0]
        self.port = address[1]
        self.frame_number = 1
        self.playing = False
        self.video : VideoProcessor = None
        self.udp_port = None
        self.rtp_socket :socket.socket = None
    
    def is_playing(self) -> bool:
        return self.playing
