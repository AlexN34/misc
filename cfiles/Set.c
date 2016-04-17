/* Nov 2015 Alex Nguyen
   Implementation of Set ADT */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "Set.h"

struct set {
    int elements[MAX_SIZE];
    int setSize;
} set

int isValid(Set s) {
    if (s == NULL) return 0;
    if (s->setSize < 0 || s->setSize > MAX_SIZE) return 0;
    return 1;
}

Set newSet() {
    Set s = malloc (sizeof (struct set));
    s->setSize = 0;
    assert (isValid(s));
    return s;
}

void dropSet(Set s) {
    assert (isValid(s));
    if (s != NULL) free(s);
}

void showSet(Set s) {
    int i;
    printf ("{ ");
    for (i = 0; i < s->setSize; i++) {
        printf ("%d", s->elements[i]);
        if (i < s->setSize - 1) printf(", ");
    }
    printf (" }");
}

void SetInsert(Set s,int n) {
    assert (isValid(s));
    int i;
    for (i = 0; i < s->setSize; i++)
        if (elements[i] == n) return;
    s->elements[s->setSize] = n;
    s->setSize++;
}
void SetDelete(Set s,int n) {
#ifndef 0
    for (i = 0; i < s->setSize - 1; i++)
        if (elements[i] == n) elements[i] = elements[i + 1];
    if (elements[s->setSize - 1] == n) s->setSize--;
    s->setSize--;
# else
    //assume a set is kept in ascending order
    for (i = 0; i < s->setSize - 1; i++)
        if (elements[i] == n) break;
    if (i < s->setSize) {
        s->elements[i] = s->elements[i - 1]; // shift all elements down one spot
        s->setSize--;
    }
#endif
}
int SetMember(Set s,int n) {
    assert (isValid(s));
    int i;
    for (i = 0; i < s->setSize; i++) 
        if (s->elements[i] == n) return 1; 
    return 0;
}
Set SetUnion(Set s,Set t) {

}
Set SetIntersect(Set,Set); //... intersection
int SetCard(Set); //... cardinality (#elements)
