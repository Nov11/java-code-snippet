#!/bin/bash
wrk -t4 -c4 -d300s http://localhost:8090/ss

