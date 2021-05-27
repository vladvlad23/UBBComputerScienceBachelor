#pragma once
#include "../Controller/Controller.h"
#include <stdio.h>


void listMedications(VectorList *);
void addMedication(VectorList *);
void deleteMedication(VectorList *);
void updateMedication(VectorList *);
void printHelp();
void startConsole(Operations *,VectorList *);