package bacis.garbagecollector;

import java.util.ArrayList;
import java.util.List;

/**
 * openjdk version "1.8.0_222"
 * OpenJDK Runtime Environment (build 1.8.0_222-8u222-b10-1ubuntu1~16.04.1-b10)
 * OpenJDK 64-Bit Server VM (build 25.222-b10, mixed mode)
 * -XX:+UseG1GC -Xms128m -Xmx128m -XX:+PrintGCDetails
 * <p>
 * [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0050103 secs]
 * [Parallel Time: 2.4 ms, GC Workers: 6]
 * [GC Worker Start (ms): Min: 115.6, Avg: 115.8, Max: 116.0, Diff: 0.3]
 * [Ext Root Scanning (ms): Min: 0.0, Avg: 1.1, Max: 2.0, Diff: 2.0, Sum: 6.4]
 * [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
 * [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
 * [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
 * [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
 * [Object Copy (ms): Min: 0.0, Avg: 0.1, Max: 0.4, Diff: 0.4, Sum: 0.9]
 * [Termination (ms): Min: 0.0, Avg: 1.0, Max: 1.6, Diff: 1.6, Sum: 5.7]
 * [Termination Attempts: Min: 1, Avg: 1.8, Max: 4, Diff: 3, Sum: 11]
 * [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
 * [GC Worker Total (ms): Min: 2.0, Avg: 2.2, Max: 2.4, Diff: 0.4, Sum: 13.0]
 * [GC Worker End (ms): Min: 118.0, Avg: 118.0, Max: 118.0, Diff: 0.0]
 * [Code Root Fixup: 0.0 ms]
 * [Code Root Purge: 0.0 ms]
 * [Clear CT: 0.1 ms]
 * [Other: 2.5 ms]
 * [Choose CSet: 0.0 ms]
 * [Ref Proc: 2.3 ms]
 * [Ref Enq: 0.0 ms]
 * [Redirty Cards: 0.1 ms]
 * [Humongous Register: 0.0 ms]
 * [Humongous Reclaim: 0.0 ms]
 * [Free CSet: 0.0 ms]
 * [Eden: 3072.0K(22528.0K)->0.0B(21504.0K) Survivors: 0.0B->1024.0K Heap: 46572.5K(128.0M)->44247.0K(128.0M)]
 * [Times: user=0.01 sys=0.00, real=0.01 secs]
 * [GC concurrent-root-region-scan-start]
 * [GC concurrent-root-region-scan-end, 0.0004397 secs]
 * [GC concurrent-mark-start]
 * [GC concurrent-mark-end, 0.0000812 secs]
 * [GC remark [Finalize Marking, 0.0027745 secs] [GC ref-proc, 0.0001307 secs] [Unloading, 0.0004966 secs], 0.0035747 secs]
 * [Times: user=0.01 sys=0.00, real=0.00 secs]
 * [GC cleanup 47247K->47247K(128M), 0.0003459 secs]
 * [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [GC pause (G1 Humongous Allocation) (young) (to-space exhausted), 0.0054753 secs]
 * [Parallel Time: 3.6 ms, GC Workers: 6]
 * [GC Worker Start (ms): Min: 145.5, Avg: 145.6, Max: 145.7, Diff: 0.2]
 * [Ext Root Scanning (ms): Min: 0.0, Avg: 0.6, Max: 3.4, Diff: 3.4, Sum: 3.6]
 * [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
 * [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
 * [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
 * [Code Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.9, Diff: 0.9, Sum: 0.9]
 * [Object Copy (ms): Min: 0.0, Avg: 0.5, Max: 1.9, Diff: 1.9, Sum: 3.1]
 * [Termination (ms): Min: 0.0, Avg: 2.0, Max: 3.2, Diff: 3.2, Sum: 12.0]
 * [Termination Attempts: Min: 1, Avg: 1.0, Max: 1, Diff: 0, Sum: 6]
 * [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
 * [GC Worker Total (ms): Min: 3.2, Avg: 3.3, Max: 3.4, Diff: 0.2, Sum: 19.6]
 * [GC Worker End (ms): Min: 148.9, Avg: 148.9, Max: 149.0, Diff: 0.1]
 * [Code Root Fixup: 0.0 ms]
 * [Code Root Purge: 0.0 ms]
 * [Clear CT: 0.0 ms]
 * [Other: 1.9 ms]
 * [Evacuation Failure: 0.5 ms]
 * [Choose CSet: 0.0 ms]
 * [Ref Proc: 1.3 ms]
 * [Ref Enq: 0.0 ms]
 * [Redirty Cards: 0.0 ms]
 * [Humongous Register: 0.0 ms]
 * [Humongous Reclaim: 0.0 ms]
 * [Free CSet: 0.0 ms]
 * [Eden: 1024.0K(21504.0K)->0.0B(6144.0K) Survivors: 1024.0K->0.0B Heap: 95677.8K(128.0M)->95677.8K(128.0M)]
 * [Times: user=0.01 sys=0.00, real=0.00 secs]
 * [Full GC (Allocation Failure)  95677K->95002K(128M), 0.0018323 secs]
 * [Eden: 0.0B(6144.0K)->0.0B(6144.0K) Survivors: 0.0B->0.0B Heap: 95677.8K(128.0M)->95002.3K(128.0M)], [Metaspace: 3037K->3037K(1056768K)]
 * [Times: user=0.00 sys=0.00, real=0.01 secs]
 * [Full GC (Allocation Failure)  95002K->94983K(128M), 0.0017879 secs]
 * [Eden: 0.0B(6144.0K)->0.0B(6144.0K) Survivors: 0.0B->0.0B Heap: 95002.3K(128.0M)->94983.4K(128.0M)], [Metaspace: 3037K->3037K(1056768K)]
 * [Times: user=0.00 sys=0.00, real=0.00 secs]
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * at bacis.garbagecollector.HumongousAllocationGcLog.main(HumongousAllocationGcLog.java:12)
 * Heap
 * garbage-first heap   total 131072K, used 94983K [0x00000000f8000000, 0x00000000f8100400, 0x0000000100000000)
 * region size 1024K, 1 young (1024K), 0 survivors (0K)
 * Metaspace       used 3068K, capacity 4496K, committed 4864K, reserved 1056768K
 * class space    used 327K, capacity 388K, committed 512K, reserved 1048576K
 * <p>
 * Process finished with exit code 1
 */

//oom
public class HumongousAllocationGcLog {
    private static List<byte[]> holder = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            holder.add(new byte[1500 * 1024]);
        }
    }
}
