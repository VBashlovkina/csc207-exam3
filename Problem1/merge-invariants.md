Problem 1: Invariants for `merge`
=================================
Notes

Referred to merge(Comparator<T> order, T[] a1, T[] a2) in Utils.java
from Sorting lab.

a. Pictorial invariants
-----------------------

_Sketch the lop invariants for the `merge` procedure.  ASCII art
would be great, since it's what we normally use, but if you want to
use a drawing program or even scan a hand-drawn sketch, that's fine._

Cursor in a1: cur1, lb1 <= cur1 <= ub1
Cursor in a2: cur2, lb2 <= cur2 <= ub2
Cursor in m:  curM, 0 <= curM <= m.length

    a1:
    +----------+-----------+----------+----------+----------+
    |          |copied to m|     not yet in m    |          |
    +----------+-----------+----------+----------+----------+
    |          |           |                     |          |
    0          lb1         cur1                  ub1        a1.length

    a2:
    +----------+-----------+----------+----------+----------+
    |          |copied to m|not in m  |          |          |
    +----------+-----------+----------+----------+----------+
    |          |           |          |                     |
    0          lb2         cur2       ub2                   a2.length


    m:
    +----------+-----------+----------+----------+-----------+
    |       sorted         |            empty                |
    +----------+-----------+----------+----------+-----------+
    |                      |                                 |
    0                      curM                              m.length

b. Textual Invariants
---------------------

_Express your invariant using a similar level of formality to that
we use in the preconditions and postconditions above._

1) For all lb1 =< i < cur1, we can fix 0 <= j < curM such that
m[j] == a1[i].
For all lb1 > i or i >= cur1, 0 <= j < curM, m[j] != a1[i].

2) For all lb2 =< i < cur2, we can fix 0 <= j < curM such that
m[j] == a2[i].
 For all lb2 > i or i >= cur2, 0 <= j < curM, m[j] != a2[i].

3) For all 0 < i < curM, order.compare(m[i-1],m[i]) <= 0.

c. Initial State
----------------

_Explain how we can ensure that the loop invariant holds at
the beginning of `merge`._

At the beginning of `merge` cur1 = lb1, cur2 = lb2, curM = 0, so 
all invariants hold:
1) lb1 =< i < cur1 is an empty set since lb1 == cur1
2) lb2 =< i < cur2 is an empty set since lb2 == cur2
3) 0 < i <= curM is an empty set because curM == 0

d. Loop Body
------------

_Explain, in English, what should happen in each iteration of
the main loop._

 In each iteration, a1[cur1] is compared to a2[cur2]. 
If a1[cur1] is smaller and cur1 is within bounds (smaller than ub1),
or if cur2 is out of bounds (greater than or equal to ub2)
then m[curM] is assigned to be a1[cur1]. cur1 is incremented.
Otherwise, m[curM] is assigned to be a2[cur2]. cur2 is incremented.
After the conditional, curM is incremented.

e. Loop Termination
-------------------

_Give a loop termination condition._

The loop signature would be `while (curM < m.length)` so it will terminate
when curM == m.length.

f. Additional Work
------------------

_Do you need to do any work after the loop terminates?  If so,
describe that work.  You may find it useful to explain it in terms
of the loop invariants._

No additional work is needed (other than returning m).

g. Postconditions
-----------------

_Explain why the postconditions are met.  Your explanation should
involve the loop invariants, the postconditions, and any additional
work you describe._

When loop terminates, curM == m.length. According to loop invariant #3,
for all 0 < i < curM, order.compare(m[i-1],m[i]) <= 0, so we have that
for all 0 < i < m.length, order.compare(m[i-1],m[i]) <= 0, which means 
that the "sorted" postcondition is met.
Note that the loop must have done m.length many operations, which by
construction should be equal to (ub1 - lb1) + (ub2 - lb2) - the sum of the
ranges of cur1 and cur2. In each iteration either cur1 or cur2 got
 incremented, and once one of them got out of its bounds, it was no longer
 allowed to be incremented. Therefore, at the end of the loop cur1 == ub1
and cur2 == ub2. Then, by invariants #1 and #2, for all lb1 =< i < ub1,
 we can fix 0 <= j < m.length such that m[j] == a1[i] and for all
 lb2 =< i < ub2, we can fix 0 <= j < m.length such that m[j] == a2[i]. In
other words, all elements of a1 and a2 (within respective bounds) appear
in m. Thus, the postcondition of m being a permutation of the concatenation
of given subarrays of a1 and a2 is also met.
