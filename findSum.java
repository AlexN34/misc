
//Brute force algorithm

boolean findSum (IntArray S, int x) {
    n <- length[S]
    for i = 0 to n do
        for j = 0 to n do
            next if i = j
            return true if S[i] + S[j] = x
        end for 
    end for
    return false
}

//algorithm including Binary search (hopefully)

boolean findSum (IntArray S, int x) {
    n <- length[S]
    MergeSort(S,0,n)
    //Array is now sorted. MergeSort has O(nlogn)
    for i = 0 to n do
        remainder = x - S[i]
        return true if findInArray(S, remainder, 0, n) //bool returned
    end for
    return false
}

//return index containing the match
int findInArray(int array, int val, int lo, int hi)
{
    if (hi <= lo) return lo;
    int mid = (hi+lo)/2;
    int diff = cmp(k, key(a[mid]));
    if (diff < 0)
        return findInArray(k, a, lo, mid);
    else if (diff > 0)
        return findInArray(k, a, mid+1, hi);
    else
        return mid;
}



int findInArray(int array, int val, int lo, int hi)
{
    if hi = lo && a[lo] = val  //perhaps check it's in range of array
        return true
    else 
        return false

    int mid = (lo + hi) /2
    if array[mid] > val findInArray(array, val, lo, mid)
    else if array[mid] < val findInArray(array, val, mid +1, hi)
    else return true //if array[mid] == val 
}

