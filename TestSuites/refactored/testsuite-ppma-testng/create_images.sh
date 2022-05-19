#!/bin/bash
wtds=$(ls reaching_wtds/)
for wtd in $wtds
do
	testname=$(echo $wtd | sed "s/\\.wtd//g")
	lowername=$(echo $testname | tr '[:upper:]' '[:lower:]')
	echo "Creating container for test $testname"
	./run-docker.sh -p yes -n ppma
	sleep 5
	./run-warranted.sh reaching_wtds/$wtd
	sleep 5
	docker commit ppma ppma-$lowername
	sleep 5
	./teardown-docker.sh ppma
	sleep 2
done