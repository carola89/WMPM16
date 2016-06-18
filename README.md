Project for 188.924 Workflow Modeling and Process Management TU Wien

Generic interface für smart home

# WMPM16

## Documentation

To start the SMPPSim (SMS part)
sh startsmppsim.sh
in the directory SMPPSim

To start the H2 DB in Server Mode run:

	java -cp h2-1.4.191.jar org.h2.tools.Server


A local FTP-Server with User "test" and no password has to be started.

For local installation (Windows only) we used FileZilla with standard configuration.

The local FTP-URL is: ftp://test@localhost:21



To Access H2 use following JDBC Connection URL:

	jdbc:h2:tcp://localhost/~/wmpm16
	User: sa
	No Password

To Access H2 Console in Browser use following URL:

	http://localhost:8082/login.jsp
	
	Driver: org.h2.Driver
	JDBC URL: jdbc:h2:tcp://localhost/~/wmpm16		
	User: sa
	No Password
