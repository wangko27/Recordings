#!/bin/sh

dumpfile=$1
username=jrrecord
password=jrsi1970
host=209.204.83.241
database=jrrecord

mysql -h$host -u$username -p$password $database < $dumpfile
