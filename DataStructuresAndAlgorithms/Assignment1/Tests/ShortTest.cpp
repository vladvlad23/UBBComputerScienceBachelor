#include "ShortTest.h"
#include "../DynamicArray.h"
#include "../DAIterator.h"
#include <assert.h>

using namespace std;

void testAll() { 
	DynamicArray da(4);
	assert(da.size() == 0); 
	da.addToEnd(5);
	da.addToPosition(1, 1);
	da.addToPosition(0, 10);
	da.addToPosition(1, 7);
	da.addToPosition(1, 1);
	da.addToPosition(1, 11);
	da.addToPosition(1, -3);
	assert(da.size() == 7);
	assert(da.remove(1) == -3);
	assert(da.remove(4) == 5);
	assert(da.size() == 5);
	assert(da.setElement(0, 7) == 10);
	assert(da.size() == 5);
	DAIterator it = da.iterator();
	int vals[] = { 7,11,1,7,1 };
	it.first();
	int k = 0;
	while (it.valid()) {
		TElem e = it.getCurrent();
		assert(e == vals[k++]);
		it.next();
	}
}