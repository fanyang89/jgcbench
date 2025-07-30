# JGCBench

JGCBench is a Java benchmark tool designed to compare the performance of different Garbage Collection (GC) algorithms in the JVM, specifically focusing on G1GC and ZGC.

## Overview

This benchmark creates a workload that allocates and deallocates memory in a sliding window pattern to simulate an application with a steady state heap usage. The benchmark runs for 60 seconds and reports the number of allocation/deallocation cycles completed.

The tool also includes scripts to analyze GC logs from both G1GC and ZGC to extract pause time statistics.

## How It Works

The benchmark maintains a sliding window of byte arrays:
1. It allocates `WINDOW` number of byte arrays (each 1KB in size) and adds them to the end of a deque
2. It then removes the same number of byte arrays from the beginning of the deque
3. This cycle repeats for 60 seconds
4. The benchmark reports the total number of cycles completed

The sliding window size is calculated to maintain approximately 500MB of live objects in the heap at any given time.

## Prerequisites

- Java 21
- Maven
- Task (for running benchmarks with predefined tasks)

## Building

To build the project, run:

```bash
mvn clean package
```

This will create a jar file with dependencies in the `target` directory.

## Running the Benchmark

### Using Task

If you have `task` installed, you can run the predefined benchmarks:

```bash
# Run G1GC benchmark
task g1gc

# Run ZGC benchmark
task zgc
```

### Manual Execution

You can also run the benchmarks manually:

```bash
# Run with G1GC
java -XX:+UseG1GC -Xmx2g -Xms2g \
     -Xlog:gc*:g1gc.log:time,uptime \
     -jar target/jgcbench-1.0-SNAPSHOT-jar-with-dependencies.jar

# Run with ZGC
java -XX:+UseZGC -Xmx2g -Xms2g \
     -Xlog:gc*:zgc.log:time,uptime \
     -jar target/jgcbench-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Analyzing Results

After running the benchmarks, you can analyze the GC logs:

```bash
# Analyze G1GC results
cat g1gc.log | ./scripts/g1gc.sh

# Analyze ZGC results
cat zgc.log | ./scripts/zgc.sh
```

The analysis scripts will output statistics on GC pause times including minimum, maximum, and average pause times.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.