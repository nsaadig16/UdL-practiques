#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 100

FILE *fitxer_traç;

int comparador(const void *a, const void *b) {
    fprintf(fitxer_traç, "2 %p\n", a);
    fprintf(fitxer_traç, "2 %p\n", b);
    return (*(int*)b - *(int*)a);
}

void processar_array(int *desti, int *mida_desti, int valor) {
    if (*mida_desti == 0 || desti[*mida_desti - 1] != valor) {
        fprintf(fitxer_traç, "2 %p\n", &valor);
        fprintf(fitxer_traç, "2 %p\n", &mida_desti);
        fprintf(fitxer_traç, "2 %p\n", &desti[*mida_desti - 1]);
        desti[*mida_desti] = valor;
        fprintf(fitxer_traç, "3 %p\n", &desti[*mida_desti]);
        (*mida_desti)++;
        fprintf(fitxer_traç, "3 %p\n", mida_desti);
    }
}

void mostrar_array(const char *titol, int *array, int mida) {
    printf("\n%s (%d elements):\n", titol, mida);
    for (int i = 0; i < mida; i++) {
        printf("%d ", array[i]);
        fprintf(fitxer_traç, "2 %p\n", &array[i]);
    }
    printf("\n");
}

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
    fprintf(fitxer_traç, "2 %p\n", &a);
    fprintf(fitxer_traç, "3 %p\n", &a);
    fprintf(fitxer_traç, "2 %p\n", &b);
    fprintf(fitxer_traç, "3 %p\n", &b);
    fprintf(fitxer_traç, "2 %p\n", &temp);
    fprintf(fitxer_traç, "3 %p\n", &temp);

}

int partition(int array[], int low, int high) {
    int pivot = array[high];
    int i = low - 1;
    fprintf(fitxer_traç, "2 %p\n", &array[high]);
    fprintf(fitxer_traç, "3 %p\n", &pivot);
    fprintf(fitxer_traç, "2 %p\n", &low);
    fprintf(fitxer_traç, "3 %p\n", &i);

    for (int j = low; j < high; j++) {
        if (array[j] >= pivot) {
            i++;
            swap(&array[i], &array[j]);
        }
        fprintf(fitxer_traç, "2 %p\n", &high);
        fprintf(fitxer_traç, "2 %p\n", &low);
        fprintf(fitxer_traç, "2 %p\n", &array[i]);
        fprintf(fitxer_traç, "2 %p\n", &array[j]);
    }
    swap(&array[i + 1], &array[high]);
    fprintf(fitxer_traç, "2 %p\n", &array[i+1]);
    fprintf(fitxer_traç, "2 %p\n", &array[high]);
    fprintf(fitxer_traç, "2 %p\n", &i); 

    return i + 1;
}

void quicksort(int array[], int low, int high) {
    if (low < high) {
        fprintf(fitxer_traç, "2 %p\n", &low);
        fprintf(fitxer_traç, "2 %p\n", &high);
        int pivotIndex = partition(array, low, high);
        fprintf(fitxer_traç, "3 %p\n", &pivotIndex);
        quicksort(array, low, pivotIndex - 1);
        quicksort(array, pivotIndex + 1, high);
    }
}


int main() {
    fitxer_traç = fopen("tr_calcul_array.prg", "w");
    if (!fitxer_traç) {
        perror("Error en crear el fitxer de traça");
        return 1;
    }

    FILE *fitxer = fopen("valors.txt", "r");
    if (!fitxer) {
        perror("Error en obrir el fitxer valors.txt");
        fclose(fitxer_traç);
        return 1;
    }

    int original[MAX], ordenat[MAX], parells[MAX], imparells[MAX];
    int mida_parells = 0, mida_imparells = 0;
    fprintf(fitxer_traç, "3 %p\n", &mida_parells);
    fprintf(fitxer_traç, "3 %p\n", &mida_imparells);


    for (int i = 0; i < MAX; i++) {
        if (fscanf(fitxer, "%d", &original[i]) != 1) {
            printf("Error\n");
            fclose(fitxer);
            fclose(fitxer_traç);
            return 1;
        }
        fprintf(fitxer_traç, "2 %p\n", &i);
        fprintf(fitxer_traç, "3 %p\n", &original[i]);
    }
    fclose(fitxer);

    for (int i = 0; i < MAX; i++) {
        ordenat[i] = original[i];
        fprintf(fitxer_traç, "2 %p\n", &original[i]); 
        fprintf(fitxer_traç, "3 %p\n", &ordenat[i]); 
    }

    quicksort(ordenat,0, MAX -1);
    
    for (int i = 0; i < MAX; i++) {
        fprintf(fitxer_traç, "2 %p\n", &ordenat[i]);
        if (ordenat[i] % 2 == 0) {
            processar_array(parells, &mida_parells, ordenat[i]);
        } else {
            processar_array(imparells, &mida_imparells, ordenat[i]);
        }
    }

    mostrar_array("Array ordenat", ordenat, MAX);
    mostrar_array("Nombres parells", parells, mida_parells);
    mostrar_array("Nombres imparells", imparells, mida_imparells);

    fclose(fitxer_traç);
    return 0;
}