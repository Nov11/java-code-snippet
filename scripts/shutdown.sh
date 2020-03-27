#!/bin/bash
jps |grep GracefulShutDown | cut -d ' ' -f 1 | xargs kill
