#include "ExtendedTest.h"
#include <assert.h>
#include "../DAIterator.h"
#include "../DynamicArray.h"
#include <exception>

using namespace std;

void testCreate() {
	DynamicArray da(10);
	assert(da.size() == 0);
	DAIterator it = da.iterator(); 
	assert(it.valid() == false);
}

void testAdd() {
	DynamicArray da(5); 
	for (int i = 0; i < 5; i++) {
		da.addToEnd(i);
	}
	da.addToPosition(da.size(), 5);
	da.addToPosition(da.size(), 6);
	da.addToPosition(da.size(), 7);
	assert(da.size() == 8);
	da.addToPosition(0, 10);
	da.addToPosition(1, 11);
	da.addToPosition(2, 12);
	da.setElement(da.size() - 1, 9);
	assert(da.size() == 11);
	int vals[] = { 10,11,12,0,1,2,3,4,5,6,9 };
	DAIterator it = da.iterator();
	it.first();
	int k = 0;
	while (it.valid()) {
		TElem e = it.getCurrent();
		assert(e == vals[k]);
		k++;
		it.next();
	}
	//test going through
	for (int i = 0; i < da.size(); i++) {
		TElem e = da.getElement(i);
		assert(e == vals[i]);
	}
}

void testRemove() {
	DynamicArray da(20);
	for (int i = -100; i < 100; i = i + 2) {
		da.addToEnd(i);
	}
	assert(da.size() == 100);
	int val = 98;
	for (int i = 0; i < 100; i++) { 
		assert(da.remove(da.size()- 1) == val);
		val -= 2;
	}
	assert(da.size() == 0);
	for (int i = -100; i <= 100; i = i + 2) { 
		da.addToEnd(i);
	}	
	assert(da.remove(da.size() - 1) == 100);
	assert(da.size() == 100);	
	val = -100;
	for (int i = 0; i < 100; i++) {
		assert(da.remove(0) == val);
		val += 2;
	}
	assert(da.size() == 0);
	for (int i = -100; i <= 100; i++) { 
		da.addToEnd(i);
	}
	int i = 0;
	while (i < da.size()) {
		TElem e = da.getElement(i);
		if (e % 2 == 0) 
			e = da.remove(i);
		else
			i++;
	}
	DAIterator it = da.iterator();
	it.first();
	val = -99;
	while (it.valid()) {
		TElem e = it.getCurrent();
		assert(e == val);
		val += 2;
		it.next();
	}
	val = -99;
	for (int i = 0; i < da.size(); i++) {
		TElem e = da.getElement(i);
		assert(e == val);
		val += 2;
	}
}


void testIterator() { 
	DynamicArray da(10);
	for (int i = 0; i < 9; i = i + 2) { 
		da.addToEnd(i);
	}
	DAIterator it = da.iterator();
	assert(it.valid() == true);
	it.first();
	int k = 0;
	while (it.valid()) {
		TElem e1 = it.getCurrent();
		TElem e2 = da.getElement(k);
		assert(e1 == e2);
		it.next();
		k++;
	}
}

void testQuantity() {
	DynamicArray da(100);
	for (int i = 0; i < 200000; i++)
		da.addToEnd(i);
	assert(da.size() == 200000);
	DAIterator it = da.iterator();
	assert(it.valid() == true);
	for (int i = 0; i < da.size(); i++) {
		it.next();
	}
	it.first();
	int i = 0;
	while (it.valid()) { 
		TElem e = it.getCurrent();
		assert(e == i);
		i++;
		it.next();
	}
	assert(it.valid() == false);
	for (int i = da.size() - 1; i >= 0; i--) {
		assert(da.remove(da.size() - 1) == i);
	}
	assert(da.size() == 0);
}

void testExceptions() {
	DynamicArray da(10);
	try {
		da.getElement(0);
		assert(false);
	}
	catch (exception&) {
		assert(true); 
	}
	try {
		TElem e = da.remove(1);
		assert(false);
	}
	catch (exception&) {
		assert(true); 
	}
	try {
		da.setElement(0, 1);
		assert(false);
	}
	catch (exception&) {
		assert(true); 
	}
	try {
		assert(da.size() == 0);
	}
	catch (exception&) {
		assert(false); 
	}
	try {
		DynamicArray da2(-2);
		assert(false);
	}
	catch (exception&) {
		assert(true);
	}
}



void testAllExtended() {
	testCreate();
	testAdd();
	testRemove();
	testIterator();
	testQuantity();
	testExceptions();
}