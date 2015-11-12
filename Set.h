/* Interface for Set ADT
   Alex Nguyen Nov 2015 */



Set newSet();

void dropSet(Set);

void showSet(Set);

void SetInsert(Set,int); //... add number into set
void SetDelete(Set,int); //... remove number from set
int SetMember(Set,int); //... set membership test
Set SetUnion(Set,Set); //... union
Set SetIntersect(Set,Set); //... intersection
int SetCard(Set); //... cardinality (#elements)