#!/bin/sh

dumpfile=$1
username=jrrecord
password=jrsi1970

mysql -u$username -p$password jrrecord < $dumpfile
