#include "./UserInterface/UserInterface.h"
#include "./Tests/SpecificTestToMedicine.h"

int main()
{
	performTests();
	testUndoRedo();
	testUndoRedoSecondEdition();
	VectorList *medicationList;
	int undoType = 2;
	if(undoType == 1)
    {
        Operations *operations;
        printHelp();
        VectorCreate(&medicationList);
        addInitialElements(medicationList);
        createOperations(&operations,medicationList);
        startConsole(operations,medicationList);
        VectorDestroyElementNames(medicationList);
        VectorDestroy(&medicationList);
        destroyOperations(&operations);
    }
	else if(undoType == 2)
    {
	    OperationsSecondEdition operations;
	    printHelp();
	    VectorCreate(&medicationList);
	    addInitialElements(medicationList);
	    createOperationsSecondEdition(&operations);
    }
	return 0;
}