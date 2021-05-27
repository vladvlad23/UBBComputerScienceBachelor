#include "./UserInterface/UserInterface.h"
#include "./Tests/SpecificTestToMedicine.h"

int main()
{
	performTests();
	testUndoRedo();
	VectorList *medicationList;
	Operations *operations;
	printHelp();
	VectorCreate(&medicationList);
	addInitialElements(medicationList);
	createOperations(&operations,medicationList);
	startConsole(operations,medicationList);
	VectorDestroyElementNames(medicationList);
    VectorDestroy(&medicationList);
    destroyOperations(&operations);
	return 0;
}