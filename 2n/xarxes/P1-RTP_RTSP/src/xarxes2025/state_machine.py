class StateMachine:
    def __init__(self):
        self.transitions = {
            ('INIT','SETUP') : 'READY',
            ('READY','PLAY') : 'PLAYING',
            ('READY','TEARDOWN') : 'INIT',
            ('PLAYING','PAUSE') : 'READY',
            ('PLAYING','TEARDOWN') : 'INIT'
        }
        self.state = 'INIT'
    
    def transition(self, event):
        """
        Transition to the next state based on the current state and event.
        """
        if (self.state, event) in self.transitions:
            self.state = self.transitions[(self.state, event)]
            return True
        return False
    
    def get_state(self):
        """
        Get the current state.
        """
        return self.state