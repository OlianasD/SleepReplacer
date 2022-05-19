#!/bin/bash
wtds=$(ls reaching_wtds/)
for wtd in $wtds
do
	testname=$(echo $wtd | sed "s/\\.wtd//g")
	echo "Validating image for $testname"
	imgname=$(echo $wtd | sed "s/\\.wtd//g" | tr '[:upper:]' '[:lower:]')
	echo "collabtive-$imgname"
	./run-custom-docker.sh -p yes -i "collabtive-$imgname" -n collabtive
	sleep 5
	./run-single-test.sh "tests.$testname"
	sleep 2
	./teardown-docker.sh collabtive
	sleep 2
	killall chrome
	killall chromedriver
done