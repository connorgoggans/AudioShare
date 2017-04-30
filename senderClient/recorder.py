import socket
import pyaudio
import wave
import sys

#AUDIO FORMAT VALUES
CHUNK = 1024
FORMAT = pyaudio.paInt32
CHANNELS = 2
RATE = 44100

HOST = ''    # The remote host
PORT = 50007              # The same port as used by the server

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))

if len(sys.argv) < 2:
    print("Plays a wave file.\n\nUsage: %s filename.wav" % sys.argv[0])
    sys.exit(-1)

#Open Wave file to be sent
wf = wave.open(sys.argv[1], 'rb')

#p = pyaudio.PyAudio()

#stream = p.open(format=FORMAT,
#                channels=CHANNELS,
#                rate=RATE,
#                input=True,
#                frames_per_buffer=CHUNK)


#frames = []

#for i in range(0, int(RATE/CHUNK*RECORD_SECONDS)):
 #data  = stream.read(CHUNK)
 #frames.append(data)
 #s.sendall(data)


data = wf.readframes(CHUNK);
#continually stream while data isn't null
while data != '':
    data  = wf.readframes(CHUNK)
    #frames.append(data)
    #s.sendall(data)

print("*done recording")

#stream.stop_stream()
#stream.close()
#p.terminate()
s.close()

print("*closed")
