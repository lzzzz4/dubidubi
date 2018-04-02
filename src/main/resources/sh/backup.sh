



#!/bin/bash
backupTime=`date +%Y%m%d-%T`

sudo mysqldump -u root -pLinzijie123!! gzh > /home/ubuntu/gzh_back_up/db_$backupTime.sql
