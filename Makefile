JFLAGS = -g
JC = javac
my_api_key = 3be9d9e47f0cbe1581a07e22e2ba6d88
my_city = New' 'York'\n'

run: build
	java Main $(api_key) $(city)

test: build
	java Main $(my_api_key) $(my_city)

build:
	$(JC) $(JFLAGS) *.java

clean:
	$(RM) *.class
	