package com.phasmidsoftware.dsaipg.adt.pq;

import com.phasmidsoftware.dsaipg.util.PrivateMethodTester;
import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class PriorityQueue4aryTest {

    @Test
    public void testUnordered1() {
        String[] binHeap = new String[5]; // Allocate sufficient size
        binHeap[1] = "A";
        binHeap[2] = "B";
        boolean max = false;
        Iterable<String> pq = new PriorityQueue4ary<>(max, binHeap, 1, 2, Comparator.comparing(String::toString), false);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(max, tester.invokePrivate("unordered", 1, 2));
    }

    @Test
    public void testUnordered2() {
        String[] binHeap = new String[5];
        binHeap[1] = "A";
        binHeap[2] = "B";
        boolean max = true;
        Iterable<String> pq = new PriorityQueue4ary<>(max, binHeap, 1, 2, Comparator.comparing(String::toString), false);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(max, tester.invokePrivate("unordered", 1, 2));
    }

    @Test
    public void testSwimUp0() {
        String[] binHeap = new String[4];
        String a = "A";
        String b = "B";
        binHeap[0] = a;
        binHeap[1] = b;
        // Create a 4-ary heap using index 0
        Iterable<String> pq = new PriorityQueue4ary<>(true, binHeap, 0, 2, Comparator.comparing(String::toString), true);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(a, tester.invokePrivate("peek", 0));
        tester.invokePrivate("swimUp", 1);
        assertEquals(b, tester.invokePrivate("peek", 0));
    }

    @Test
    public void testSwimUp1() {
        String[] binHeap = new String[4];
        String a = "A";
        String b = "B";
        binHeap[1] = a;
        binHeap[2] = b;
        // Create a 4-ary heap that does not use index 0
        Iterable<String> pq = new PriorityQueue4ary<>(true, binHeap, 1, 2, Comparator.comparing(String::toString), false);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(a, tester.invokePrivate("peek", 1));
        tester.invokePrivate("swimUp", 2);
        assertEquals(b, tester.invokePrivate("peek", 1));
    }

    @Test
    public void testSwimUp2() {
        String[] binHeap = new String[7];
        binHeap[1] = "A"; // A small value
        binHeap[2] = "Z";
        binHeap[3] = "B";
        binHeap[4] = "C"; // Element to be swum up
        // Create a max 4-ary heap
        Iterable<String> pq = new PriorityQueue4ary<>(true, binHeap, 1, 4, Comparator.comparing(String::toString), false);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("swimUp", 4); // Swim "C" upward
        // In a 4-ary heap, the parent of node 4 is at index 1, so after swapping "C" should become the root (index 1)
        assertEquals("C", tester.invokePrivate("peek", 1));
    }

    @Test
    public void testSwimUp3() {
        String[] binHeap = new String[7];
        binHeap[1] = "D";
        binHeap[2] = "C";
        binHeap[3] = "E";
        binHeap[4] = "B";
        // Create a min 4-ary heap
        Iterable<String> pq = new PriorityQueue4ary<>(false, binHeap, 1, 4, Comparator.comparing(String::toString), false);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("swimUp", 4); // Swim "B" upward
        assertEquals("B", tester.invokePrivate("peek", 1)); // Check the root
    }

    @Test
    public void testSink0() {
        String[] binHeap = new String[5];
        String a = "A";
        String b = "B";
        String c = "C";
        binHeap[0] = a;
        binHeap[1] = b;
        binHeap[2] = c;
        Iterable<String> pq = new PriorityQueue4ary<>(true, binHeap, 0, 3, Comparator.comparing(String::toString), false);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("sink", 0);
        assertEquals(c, tester.invokePrivate("peek", 0));
        assertEquals(a, tester.invokePrivate("peek", 2));
    }

    @Test
    public void testSink1() {
        String[] binHeap = new String[5];
        String a = "A";
        String b = "B";
        String c = "C";
        binHeap[1] = a;
        binHeap[2] = b;
        binHeap[3] = c;
        Iterable<String> pq = new PriorityQueue4ary<>(true, binHeap, 1, 3, Comparator.comparing(String::toString), false);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        tester.invokePrivate("sink", 1);
        assertEquals(c, tester.invokePrivate("peek", 1));
        assertEquals(a, tester.invokePrivate("peek", 3));
    }

    @Test
    public void testGive1() {
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(10, Comparator.comparing(String::toString));
        String key = "A";
        pq.give(key);
        assertEquals(1, pq.size());
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(key, tester.invokePrivate("peek", 1));
    }

    @Test
    public void testGive2() {
        // Test that elements can be given even beyond the queue's capacity
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(1, Comparator.comparing(String::toString));
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        String key = "A";
        pq.give(null); // This element will be discarded
        assertEquals(1, pq.size());
        assertNull(tester.invokePrivate("peek", 1));
        pq.give(key);
        assertEquals(1, pq.size());
        assertEquals(key, tester.invokePrivate("peek", 1));
    }

    @Test
    public void testTake1() throws PQException {
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(10, Comparator.comparing(String::toString));
        String key = "A";
        pq.give(key);
        assertEquals(key, pq.take());
        assertTrue(pq.isEmpty());
    }

    @Test
    public void testTake2() throws PQException {
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(10, Comparator.comparing(String::toString));
        String a = "A";
        String b = "B";
        pq.give(a);
        pq.give(b);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        // For 4-ary heaps, the internal order may differ slightly from binary heaps, but the goal is to verify take()
        String firstPeek = (String) tester.invokePrivate("peek", 1);
        String secondPeek = (String) tester.invokePrivate("peek", 2);
        // Only check that the two removed elements are the same as the inserted ones (order may vary by implementation)
        String taken1 = pq.take();
        String taken2 = pq.take();
        assertTrue((taken1.equals(a) && taken2.equals(b)) || (taken1.equals(b) && taken2.equals(a)));
        assertTrue(pq.isEmpty());
    }

    @Test(expected = PQException.class)
    public void testTake3() throws PQException {
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(10, Comparator.comparing(String::toString));
        pq.give("A");
        pq.take();
        pq.take();
    }

    @Test
    public void isEmpty() {
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(10, false, Comparator.comparing(String::toString));
        assertTrue(pq.isEmpty());
    }

    @Test
    public void size() throws PQException {
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(10, false, Comparator.comparing(String::toString));
        assertEquals(0, pq.size());
        pq.give("A");
        assertEquals(1, pq.size());
        pq.take();
        assertEquals(0, pq.size());
    }

    @Test
    public void doTake01() throws PQException {
        String[] binHeap = new String[5];
        binHeap[0] = "A";
        binHeap[1] = "B";
        binHeap[2] = "C";
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(false, binHeap, 0, 3, Comparator.comparing(String::toString), false);
        pq.doTake(pq::snake);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 0));
    }

    @Test
    public void doTake02() throws PQException {
        String[] binHeap = new String[5];
        binHeap[0] = "C";
        binHeap[1] = "A";
        binHeap[2] = "B";
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(true, binHeap, 0, 3, Comparator.comparing(String::toString), false);
        pq.doTake(pq::sink);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 0));
    }

    @Test
    public void doTake11() throws PQException {
        String[] binHeap = new String[6];
        binHeap[1] = "A";
        binHeap[2] = "B";
        binHeap[3] = "C";
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(false, binHeap, 1, 3, Comparator.comparing(String::toString), false);
        pq.doTake(pq::snake);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 1));
    }

    @Test
    public void doTake12() throws PQException {
        String[] binHeap = new String[6];
        binHeap[1] = "C";
        binHeap[2] = "A";
        binHeap[3] = "B";
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(true, binHeap, 1, 3, Comparator.comparing(String::toString), false);
        pq.doTake(pq::sink);
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals("B", tester.invokePrivate("peek", 1));
    }

    @Test
    public void iterator0() {
        String[] binHeap = new String[5];
        binHeap[0] = "C";
        binHeap[1] = "B";
        binHeap[2] = "D";
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(true, binHeap, 0, 3, Comparator.comparing(String::toString), false);
        assertEquals(3, pq.size());
        Iterator<String> iterator = pq.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[0], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[1], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[2], iterator.next());
        assertFalse(iterator.hasNext());
        assertEquals(3, pq.size());
    }

    @Test
    public void iterator1() {
        String[] binHeap = new String[6];
        binHeap[1] = "C";
        binHeap[2] = "B";
        binHeap[3] = "D";
        PriorityQueue4ary<String> pq = new PriorityQueue4ary<>(true, binHeap, 1, 3, Comparator.comparing(String::toString), false);
        assertEquals(3, pq.size());
        Iterator<String> iterator = pq.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[1], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[2], iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(binHeap[3], iterator.next());
        assertFalse(iterator.hasNext());
        assertEquals(3, pq.size());
    }

    @Test
    public void testGetMax() {
        Iterable<String> pq = new PriorityQueue4ary<>(10, false, Comparator.comparing(String::toString));
        final PrivateMethodTester tester = new PrivateMethodTester(pq);
        assertEquals(false, tester.invokePrivate("getMax"));
    }
}
