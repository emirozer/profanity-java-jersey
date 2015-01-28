#WIP

##USAGE
--

Clone the repository.

	git clone https://github.com/emirozer/profanity-java-jersey.git
	
Initialize and update the submodule

	git submodule init
	git submodule update
	
Clean and test
	
	mvn clean test
	
Run

	mvn exec:java


You will be able to reach the profanity server & functionality through:

	localhost:8080/pjj/filter/
	
**Sample Query**:
	
	 localhost:8080/pjj/filter/?input="emir is an asshole sometimes"&lang="en"
	 
*If you are unsure of the lang send an empty string; ""*
	