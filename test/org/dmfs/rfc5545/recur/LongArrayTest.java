package org.dmfs.rfc5545.recur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;


public class LongArrayTest
{

  @Test
  public void testDeduplicate()
  {
    LongArray l1 = new LongArray();
    l1.add(1);
    l1.add(2);
    l1.add(3);
    l1.add(4);
    l1.add(5);
    l1.add(6);

    l1.deduplicate();
    assertEquals(6, l1.size());
    assertEquals(1, l1.next());
    assertEquals(2, l1.next());
    assertEquals(3, l1.next());
    assertEquals(4, l1.next());
    assertEquals(5, l1.next());
    assertEquals(6, l1.next());
    assertFalse(l1.hasNext());

    LongArray l2 = new LongArray();
    l2.add(1);
    l2.add(1);
    l2.add(1);
    l2.add(1);
    l2.add(2);
    l2.add(2);
    l2.add(2);
    l2.add(2);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(3);
    l2.add(4);
    l2.add(5);
    l2.add(5);
    l2.add(5);
    l2.add(5);
    l2.add(5);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);
    l2.add(6);

    l2.deduplicate();
    assertEquals(6, l2.size());
    assertEquals(1, l2.next());
    assertEquals(2, l2.next());
    assertEquals(3, l2.next());
    assertEquals(4, l2.next());
    assertEquals(5, l2.next());
    assertEquals(6, l2.next());
    assertFalse(l2.hasNext());

  }

}
