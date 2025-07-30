#!/bin/bash

awk '
/Pause / {
    match($0, /([0-9]+[.][0-9]+)ms/, m)
    if (m[1] != "") {
        idx++
        # printf "[%3d] %s ms\n", idx, m[1]
        sum += m[1]
        min = (min=="" || m[1]<min) ? m[1] : min
        max = (max=="" || m[1]>max) ? m[1] : max
    }
}
END {
    if (idx>0) {
        printf "\nSummary:  min=%.3f ms  max=%.3f ms  avg=%.3f ms  count=%d\n",
               min, max, sum/idx, idx
    } else {
        print "No Pause line found."
    }
}'
