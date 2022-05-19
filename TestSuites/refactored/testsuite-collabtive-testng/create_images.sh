#!/bin/bash
wtds=$(ls reaching_wtds/)
for wtd in $wtds
do
	testname=$(echo $wtd | sed "s/\\.wtd//g")
	lowername=$(echo $testname | tr '[:upper:]' '[:lower:]')
	echo "Creating container for test $testname"
	./run-docker.sh -p yes -n collabtive
	sleep 5
	./run-warranted.sh reaching_wtds/$wtd
	sleep 2
	docker commit collabtive collabtive-$lowername
	sleep 2
	./teardown-docker.sh collabtive
	sleep 2
done