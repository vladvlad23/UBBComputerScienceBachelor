#include "VectorList.h"
#include "../Controller/Controller.h"
#include <stdio.h>
#include <assert.h>

//Function will create a VectorList structure in the Vector variable
//It will alocate memory to the structure itself and initialize parameters
int VectorCreate(VectorList **Vector)
{
	(*Vector) = (VectorList *)malloc(sizeof(VectorList)); //allocate memory for the vector
	(*Vector)->elements = NULL;
	(*Vector)->size = 0;
	(*Vector)->numberOfElements = 0;
	return 0;
}

//Function will destroy the names allocated dynamically to the medications

int createOperationsSecondEdition(OperationsSecondEdition *listOfOperation)
{
	listOfOperation->size = 10;
	listOfOperation->numberOfOperations = 0;
	listOfOperation->currentIndex = -1;
	listOfOperation->operations = (TypeOfOperation *) malloc(10 * sizeof(TypeOfOperation));
	return 0;
}

int doubleOperationSizeSecondEdition(OperationsSecondEdition * listOfOperation)
{
	listOfOperation->size *=2;
	TypeOfOperation * newOperations = (TypeOfOperation *) malloc(listOfOperation->size * sizeof(TypeOfOperation));
	int i;
	for(i=0;i<listOfOperation->numberOfOperations;i++)
	{
		newOperations[i] = listOfOperation->operations[i];
	}
	free(listOfOperation->operations);
	listOfOperation->operations = newOperations;
	return 0;
}


int addOperationToListSecondEdition(OperationsSecondEdition *listOfOperations,TElem elementToBeRemembered,int command,int relevantIndex)
{
	if(listOfOperations->numberOfOperations+1==listOfOperations->size)
		doubleOperationSizeSecondEdition(listOfOperations);
	listOfOperations->operations[listOfOperations->numberOfOperations].relevantIndex = relevantIndex;
	listOfOperations->currentIndex++;
	listOfOperations->operations[listOfOperations->numberOfOperations].command = command;
	listOfOperations->operations[listOfOperations->numberOfOperations].oldElement = (TElem *) malloc(sizeof(TElem));
	listOfOperations->operations[listOfOperations->numberOfOperations].oldElement->concentration = elementToBeRemembered.concentration;
	listOfOperations->operations[listOfOperations->numberOfOperations].oldElement->price = elementToBeRemembered.price;
	listOfOperations->operations[listOfOperations->numberOfOperations].oldElement->quantity = elementToBeRemembered.quantity;
	listOfOperations->operations[listOfOperations->numberOfOperations].oldElement->name = (char *) malloc(100 * sizeof(char));
	strcpy(	listOfOperations->operations[listOfOperations->numberOfOperations].oldElement->name,elementToBeRemembered.name);
	listOfOperations->numberOfOperations++;
}

int destroyOperationsSecondEdition(OperationsSecondEdition * listOfOperations)
{
	int i;
	for(i=0;i<listOfOperations->numberOfOperations;i++)
	{
		free(listOfOperations->operations[i].oldElement->name);
		free(listOfOperations->operations[i].oldElement);
	}
	free(listOfOperations->operations);
}

int undoOperationSecondEdition(OperationsSecondEdition *listOfOperations,VectorList *listOfElements)
{
	if(listOfOperations->currentIndex == -1)
		return -1;
	if(listOfOperations->operations[listOfOperations->currentIndex].command == 0)
	{
		//this means it was added. So remove it
		removeByIndex(listOfElements,listOfOperations->operations[listOfOperations->currentIndex].relevantIndex);
	}
	else if(listOfOperations->operations[listOfOperations->currentIndex].command == 1)
	{
		//this means it was removed. Se add it
		addToEnd(listOfElements,*listOfOperations->operations[listOfOperations->currentIndex].oldElement);
	}
	else if(listOfOperations->operations[listOfOperations->currentIndex].command == 2)
	{
		//this means it was updated. So remove the new one and add the previous one
		removeByIndex(listOfElements,listOfOperations->operations[listOfOperations->currentIndex].relevantIndex);
		addToEnd(listOfElements,*listOfOperations->operations[listOfOperations->currentIndex].oldElement);
	}
	listOfOperations->currentIndex--;
	return 0;
}
int redoOperationSecondEdition(OperationsSecondEdition *listOfOperations,VectorList *listOfElements)
{
	if (listOfOperations->currentIndex+1 == listOfOperations->numberOfOperations)
		return -1;
	listOfOperations->currentIndex++;
	if (listOfOperations->operations[listOfOperations->currentIndex].command == 1)
	{
		//this means it was added. so remove it
		removeByIndex(listOfElements, listOfOperations->operations[listOfOperations->currentIndex].relevantIndex);
	}
	else if (listOfOperations->operations[listOfOperations->currentIndex].command == 0)
	{
		//this means it was removed. Se add it
		addToEnd(listOfElements, *listOfOperations->operations[listOfOperations->currentIndex].oldElement);
	}
	else if (listOfOperations->operations[listOfOperations->currentIndex].command == 2)
	{
		//this means it was updated. So remove the new one and add the previous one
		removeByIndex(listOfElements, listOfOperations->operations[listOfOperations->currentIndex].relevantIndex);
		addToEnd(listOfElements, *listOfOperations->operations[listOfOperations->currentIndex].oldElement);
	}
}


int VectorDestroyElementNames(VectorList *Vector)
{
    int i;
    for(i=0;i<getNumberOfElements(Vector);i++)
    {
        free(((TElem *) Vector->elements)[i].name);
    }
    return 0;
}

//function will free the memory allocated to the elements array in the VectorList structure
int VectorDestroyElements(VectorList *Vector)
{
	if(Vector->elements) {
        free(Vector->elements);
        Vector->elements = NULL;
    }

    return 0;
}

//Function will destroy the Vector structure by freeing elements if they have not been freed already
//and will free the memory for the structure
int VectorDestroy(VectorList **Vector)
{
	int i;
	if(!(*Vector))
		return 0;
	if((*Vector)->elements!=NULL)
		free((*Vector)->elements);
    (*Vector)->elements = NULL;
cleanup:
	free(*Vector);
	return 0;
}

//Function will double the capacity of the VectorList structure by copying all the elements
//in another dynamical array. It will also free the previous array
int doubleVectorSize(VectorList *Vector)
{
	if (Vector == NULL)
		return -1; //can't assign memory
	if (!(Vector->elements))
	{
		//if the size is 0, doubling is pointless so we use 5 as a starting point
		Vector->elements = malloc(5 * sizeof(TElem));
		Vector->size = 5;
		return 0;
	}
	TElem *newElements = (TElem *)malloc(2 * Vector->size * sizeof(TElem));

	if (newElements == NULL)
		return -1; //can't assign memory
	int i;
	for (i = 0; i < Vector->numberOfElements; i++)
    {
	    newElements[i] = ((TElem *) Vector->elements)[i];
    }
	free(Vector->elements);
	Vector->elements = newElements;
	return 0;

}

//Function will add an element to the end of the list of elements in the VectorList structure
//It will also call the double capacity in case the size is insufficient
int addToEnd(VectorList *Vector, TElem Value)
{
	if (Vector == NULL)
	{
		return -1;
	}
	if (Vector->numberOfElements + 1 > Vector->size)
	{
		if (doubleVectorSize(Vector) != 0)
			return -1;
	}
	((TElem *)Vector->elements)[Vector->numberOfElements] = Value;
	Vector->numberOfElements++; //updating the numberOfElements
	return 0;
}

//Function will remove an element from the VectorList at a certain Index.
//it frees the dynamically allocated name and then overwrites the element itself (not a problem as the elements itself aren't pointers
int removeByIndex(VectorList *Vector, int Index)
{
	int i;
	free(((TElem*)Vector->elements)[Index].name);
	for (i = Index; i < Vector->numberOfElements - 1; i++) //overwrite the element in such a way that we have an empty space at the end
		((TElem *)Vector->elements)[i] = ((TElem *)Vector->elements)[i + 1];
	Vector->numberOfElements--;
	return 0;
}

//function will return the Value of an element from the given Index
// in the third parameter (as the return is reserved for succesful/unsuccesful of function
int getValueByIndex(VectorList *Vector, int Index, TElem *Value)
{
	//Ok, so this needs explaining otherwise i will forget
	//Vector->elements[Index] will get a void pointer. This one need to be cast (TElem *) and dereferenced *(TElem *)
	//before being assigned to the return value
	*Value = ((TElem *) Vector->elements)[Index];
	Value->name = (char *) malloc(sizeof(char)*100);
	strcpy(Value->name,(((TElem *) Vector->elements)[Index]).name);
	return 0;
		
}

int getNumberOfElements(VectorList *Vector)
{
	if (Vector == NULL)
		return -1;
	return Vector->numberOfElements;
}


int replaceElement(VectorList *Vector, int Index, TElem newValue)
{
	((TElem *)Vector->elements)[Index] = newValue;
	return 0;
}

void addInitialElements(VectorList *medicines)
{
	Medication medication;

	medication.name = (char *) malloc(sizeof(char)*100);
	strcpy(medication.name,"Paracetamol");
	medication.concentration = 10;
	medication.price = 10;
	medication.quantity = 100;
	assert(addToEndController(medicines, medication)==0);

	medication.name = (char *) malloc(sizeof(char)*100);
	strcpy(medication.name,"Codein");
	medication.concentration = 20;
	medication.price = 40;
	medication.quantity = 50;
	assert(addToEndController(medicines,medication)==0);

	medication.name = (char *) malloc(sizeof(char)*100);
	strcpy(medication.name,"Vicodin");
	medication.concentration = 30;
	medication.price = 60;
	medication.quantity = 35;
	assert(addToEndController(medicines, medication)==0);

	medication.name = (char *) malloc(sizeof(char)*100);
	strcpy(medication.name,"Aspirin");
	medication.concentration = 15;
	medication.price = 45;
	medication.quantity = 55;
	assert(addToEndController(medicines, medication)==0);

	medication.name = (char *) malloc(sizeof(char)*100);
	strcpy(medication.name,"Creatine");
	medication.concentration = 90;
	medication.price = 20;
	medication.quantity = 123;
	assert(addToEndController(medicines, medication)==0);

	medication.name = (char *) malloc(sizeof(char)*100);
	strcpy(medication.name,"Beta-Alanyne");
	medication.concentration = 30;
	medication.price = 10;
	medication.quantity = 15;
	assert(addToEndController(medicines, medication)==0);

	medication.name = (char *) malloc(sizeof(char)*100);
	strcpy(medication.name,"Caffeine");
	medication.concentration = 100;
	medication.price = 30;
	medication.quantity = 200;
	assert(addToEndController(medicines, medication)==0);
}

int duplicateVector(VectorList *destination,VectorList *source)
{
    destination->size=source->size;
    destination->numberOfElements=0;
    if(source->elements)
    	destination->elements = (TElem *)malloc(destination->size * sizeof(TElem));
    else
    	destination->elements = NULL;
    int i;
    for(i=0;i<getNumberOfElements(source);i++)
    {
        TElem element;
        getValueByIndex(source,i,&element);
        addToEnd(destination,element);
    }
    return 0;
}

int createOperations(Operations **operationList, VectorList *list) {
    (*operationList) = (Operations *) malloc(sizeof(Operations));
    (*operationList)->numberOfOperations=0;
    (*operationList)->size=1;
    (*operationList)->currentIndex=0;
    VectorCreate(&(*operationList)->operations);
    duplicateVector((*operationList)->operations,list);
    return 0;
}

int doubleOperationSize(Operations *operationList) {
    VectorList *newOperationList = (VectorList *) malloc(2 * operationList->size * sizeof(VectorList));
    int i;
    for(i=0;i<=operationList->numberOfOperations;i++)
    {
        newOperationList[i] = operationList->operations[i];
    }
    operationList->size*=2;
    free(operationList->operations);
    operationList->operations = newOperationList;
    return 0;
}

int addOperationToList(Operations *operationList, VectorList *listToBeAdded) {
    if(operationList->numberOfOperations+1 >= operationList->size)
    {
        doubleOperationSize(operationList);
    }
    VectorList newList;
    duplicateVector(&newList,listToBeAdded);
	operationList->numberOfOperations++;
    operationList->operations[operationList->numberOfOperations] = newList;
    operationList->currentIndex++;
}


int destroyOperations(Operations **operationList) {
	int i;
	for (i=0;i<=(*operationList)->numberOfOperations;i++) //destroy every vector list + initial state
	{
        VectorDestroyElementNames(&(*operationList)->operations[i]);
		VectorDestroyElements(&(*operationList)->operations[i]);
	}
	free((*operationList)->operations); //free memory for vector lists
	free(*operationList);
	return 0;
}

int undoOperation(Operations *operationList,VectorList *vectorList)
{
	if(operationList->currentIndex==0)
		return -1;
	operationList->currentIndex-=1;
	VectorDestroyElementNames(vectorList);
	VectorDestroyElements(vectorList);
	duplicateVector(vectorList,&operationList->operations[operationList->currentIndex]);
}
int redoOperation(Operations *operationList,VectorList*vectorList)
{
	if(operationList->currentIndex==operationList->numberOfOperations)
		return -1;
	operationList->currentIndex+=1;
	VectorDestroyElementNames(vectorList);
	VectorDestroyElements(vectorList);
	duplicateVector(vectorList,&operationList->operations[operationList->currentIndex]);


}
/*
void sort(VectorList *Vector)
{
	if (Vector == NULL)
		return -1; 
	int i, j, temp;
	for (i = 0; i < Vector->size - 1; i++)
		for (j = i + 1; j < Vector->size; j++)
		{
			if (Vector->elements[i] > Vector->elements[j]);
			{
				temp = Vector->elements[i];
				Vector->elements[i] = Vector->elements[j];
				Vector->elements[j] = temp;
			}
		}
	return 0;
}
*/