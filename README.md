SleepReplacer: a tool for replacing thread sleeps in Selenium WebDriver test suites
======
This repository contains the tool implementing the approach described in a submission to Software Quality Journal
It is organized as follows:

* **SleepReplacer/** : contains the actual tool implementing the approach
* **TestSuites/original** : contains the test suites used as experimental objects in our study
* **TestSuites/refactored** : contains the results of the execution of SleepReplacer, i.e. the refactored test suites



Tool configuration
=====

To run SleepReplacer, you need to provide some information about the test suite in a JSON configuration file, that must be placed in the root directory of the tool and named "appname_cfg.json"
Such file must contain those fields:

* **sourceProjectPath**: the absolute path of the source test suite
* **sourceProjectClassPath**: the absolute path where the classes of the source test suite are located, usually it is sourceProjectPath + "/target/classes"
* **targetProjectPath**: the absoute path of the target test suite (a copy of the source test suite where thread sleeps will be replaced with explicit waits)
* **targetLaunchClassPath**: the classpath that contains the dependencies needed to launch the target test suites

You can find all those files in SleepReplacer root directory, but you have to change absolute paths to the ones in use on your machine.



How to use SleepReplacer
=====

1) Make a copy of the source test suite (it will be the target test suite, where SleepReplacer will put the refactored tests)
2) Change the configuration file as described in section "Tool configuration" of this document
3) Create the Docker images needed for validating tests using the script "create_images.sh" in the root directory of the test suite (see Section 3.3.2 Dealing with dependencies during validation and Section 4.3 Validator component in the paper)
4) Run the tool by launching the class sleeptowait.Main. It takes 4 arguments:
	1) application name (in lower case)
	2) number of executions for the Validation step (see Section 3.3 and Section 4.3 in the paper)
	3) execution mode: use "--PO" if the test suite has page objects, "--noPO" if the test suite doesn't have page objects (all the three provided test suites do not have page objects)
	4) execution target: the whole test suite (use "--all") or only a test class (use the fully qualified test class name, for the provided test suites is "tests.className". Reccomended for a quick test of SleepReplacer) 
