Problem 1: Invariants for `merge`
=================================

a. Pictorial invariants
-----------------------

_Sketch the lop invariants for the `merge` procedure.  ASCII art
would be great, since it's wht we normally use, but if you want to
use a drawing program or even scan a hand-drawn sketch, that's fine._

    a1:
    +----------+----------+----------+----------+----------+
    |          |          |          |          |          |
    +----------+----------+----------+----------+----------+
    |                                                      |
    0                                                      a1.length

    a2:
    +----------+----------+----------+----------+----------+
    |          |          |          |          |          |
    +----------+----------+----------+----------+----------+
    |                                                      |
    0                                                      a2.length

    m:
    +----------+----------+----------+----------+----------+
    |          |          |          |          |          |
    +----------+----------+----------+----------+----------+
    |                                                      |
    0                                                      m.length

b. Textual Invariants
---------------------

_Express your invariant using a similar level of formality to that
we use in the preconditions and postconditions above._

c. Initial State
----------------

_Explain how we can ensure that the loop invariant holds at
the beginning of `merge`._

d. Loop Body
------------

_Explain, in English, what should happen in each iteration of
the main loop._

e. Loop Termination
-------------------

_Give a loop termination condition._

f. Additional Work
------------------

_Do you need to do any work after the loop terminates?  If so,
describe that work.  You may find it useful to explain it in terms
of the loop invariants._

g. Postconditions
-----------------

_Explain why the postconditions are met.  Your explanation should
involve the loop invariants, the postconditions, and any additional
work you describe._
