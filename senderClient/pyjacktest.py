import jack
try:
	client = jack.Client("myClient")
except:
	print('caught error')

in1 = client.inports.register("input_1");

out1 = client.ouports.register("output_1");
