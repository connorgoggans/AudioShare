# Echo server program
import socket
import pyaudio
import wave
import time

#AUDIO FORMAT VALUES
CHUNK = 1024
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 22050
WAVE_OUTPUT_FILENAME = "server_output.wav"
WIDTH = 2
frames = []

#New Pyaudio and stream objects
p = pyaudio.PyAudio()
stream = p.open(format=FORMAT,
                channels=CHANNELS,
                rate=RATE,
                output=True,
                frames_per_buffer=CHUNK)


HOST = ''                 # Symbolic name meaning all available interfaces
PORT = 50007              # Arbitrary non-privileged port
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1)
conn, addr = s.accept()

#Connect and Receive/Play Audio
print 'Connected by', addr
data = conn.recv(CHUNK)
while data != '':
    stream.write(data)
    data = conn.recv(CHUNK)
    frames.append(data)

wf = wave.open(WAVE_OUTPUT_FILENAME, 'wb')
wf.setnchannels(CHANNELS)
wf.setsampwidth(p.get_sample_size(FORMAT))
wf.setframerate(RATE)
wf.writeframes(b''.join(frames))
wf.close()

#Close Everything
stream.stop_stream()
stream.close()
p.terminate()
conn.close()
