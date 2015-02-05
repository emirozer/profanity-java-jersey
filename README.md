![Screenshot](https://raw.github.com/emirozer/profanity-java-jersey/master/docs/pjj.png)


##USAGE - [WIP]
######**CI Status** : ![travis](https://travis-ci.org/emirozer/profanity-java-jersey.svg?branch=master)


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

    localhost:8080/pjj/filter/?input=emir is an asshole sometimes&lang=en
    response -->  "emir is an ******* sometimes"

    localhost:8080/pjj/filter/?input=emir is an 4ssh0l3 sometimes&lang=en
    response -->  "emir is an ******* sometimes"

*If you are unsure of the lang send an empty string; ""*
