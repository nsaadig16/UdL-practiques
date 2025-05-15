/* -----------------------------------------------------------------------
 PRA1. Processos, pipes i senyals: Primers
 Codi font: calculador.c
 Nom complet Naïm Saadi Gallego
 Nom complet Eduard Térmens Botanch
 ---------------------------------------------------------------------- */

/*
----------------------------------------------------------------------
--- L L I B R E R I E S ------------------------------
----------------------------------------------------------------------
*/
#include <stdio.h> /* sprintf*/

// pid_t
#include <sys/types.h>
#include <unistd.h>

#include <stdlib.h> /* exit, EXIT_SUCCESS, ...*/
#include <string.h> /* strlen */
#include <unistd.h> /* STDOUT_FILENO */
#include <errno.h>  /* errno */
#include <signal.h>
/*
----------------------------------------------------------------------
--- C O N S T A N T S------ ------------------------------------------
----------------------------------------------------------------------
*/
#define MIDA_MAX_CADENA 1024

#define FI_COLOR "\e[0m"
#define MIDA_MAX_CADENA_COLORS 1024
#define FORMAT_TEXT_ERROR "\e[1;48;5;1;38;5;255m"

#define PIPE_LECTURA_NOMBRES 11         // Descriptors de fitxers de les pipes
#define PIPE_ESCRIPTURA_RESPOSTES 20

/*
----------------------------------------------------------------------
--- C A P Ç A L E R E S   D E   F U N C T I O N S --------------------
----------------------------------------------------------------------
*/
int ComprovarPrimer(int nombre);    
void ImprimirInfoControlador(char *text);
void ImprimirError(char *text);
void gestor_senyal(int sig);
void tractament_senyals();
/*
----------------------------------------------------------------------
--- D E F I N I C I Ó   D E   T I P U S ------------------------------
----------------------------------------------------------------------
*/
typedef enum
{
    FALS = 0,
    CERT
} t_logic;

typedef struct   //Estructura que s'ha de retornar al controlador
{
 int pid;
 int nombre;
 t_logic esPrimer;
} t_infoNombre;        

/*
----------------------------------------------------------------------
--- V A R I A B L E S   G L O B A L S --------------------------------
----------------------------------------------------------------------
*/
unsigned char numControlador;
pid_t pidPropi;
char cadena[MIDA_MAX_CADENA];
char dadesControlador[MIDA_MAX_CADENA];
int esperantSigTerm = 1;    //S'inicialitza a 1 (cert), és a dir, estem esperant a SIGTERM
char taulaColors[8][MIDA_MAX_CADENA_COLORS] = {
    "\e[01;31m", // Vermell
    "\e[01;32m", // Verd
    "\e[01;33m", // Groc
    "\e[01;34m", // Blau
    "\e[01;35m", // Magenta
    "\e[01;36m", // Cian
    "\e[00;33m", // Taronja
    "\e[1;90m"   // Gris fosc
};

/*
----------------------------------------------------------------------
--- P R O G R A M A   P R I N C I P A L ------------------------------
----------------------------------------------------------------------
*/
int main(int argc, char *argv[])
{
    
    if (argc != 2)
    {
        sprintf(cadena, "Us: %s <número calculador>\n\nPer exemple: %s 1\n\n", argv[0], argv[0]);
        write(STDOUT_FILENO, cadena, strlen(cadena));
        exit(1);
    }

    tractament_senyals();   //Aquí tractem totes les senyals
 
    pidPropi = getpid();
    numControlador = atoi(argv[1]);
    sprintf(dadesControlador, "[Calculador %u-pid:%u]> ", numControlador, pidPropi);

    sprintf(cadena, "Calculador %u activat!\n", numControlador);
    ImprimirInfoControlador(cadena);

    int num, numPrimers = 0;

    while (read(PIPE_LECTURA_NOMBRES, &num, sizeof(int)) > 0)   //Llegim del pipe de nombres fins que ens arribi un EOF
    {   
        t_logic esPrimer = (ComprovarPrimer(num) == 1 ? CERT : FALS);   //Comprovem si és primer o no

        t_infoNombre info;  //Creem una estructura on retornem tota la informació
            info.pid = pidPropi;
            info.nombre = num;
            info.esPrimer = esPrimer;
        if(esPrimer==CERT){numPrimers++;}   //Si el nombre és primer, el contem al total

        if(write(PIPE_ESCRIPTURA_RESPOSTES, &info, sizeof(info)) == -1){    //Escrivim al pipe de respostes l'estructura que hem fet
            ImprimirError("Error en l'escriptura al pipe de respostes.\n");
        };
    }
    close(PIPE_LECTURA_NOMBRES); close(PIPE_ESCRIPTURA_RESPOSTES);  //Tanquem les pipes
    
    while(esperantSigTerm){ //Aquí ens mantenim en pausa fins que ens arribi SIGTERM
        pause();
    }

    exit(numPrimers);   //Com a codi d'acabament retornem el nombre de primers trobats per aquest calculador
}


void gestor_senyal(int sig){   //Si ens arriba la senyal SIGTERM llavors fiquem la variable a 0 i així surt del bucle
    esperantSigTerm = 0;
}

void tractament_senyals(){  //Tractem SIGTERM i ignorem les altres dos
    if(signal(SIGQUIT, SIG_IGN) == SIG_ERR){
        ImprimirError("Error en tractar la senyal.\n");
    }
    if(signal(SIGINT, SIG_IGN) == SIG_ERR){
        ImprimirError("Error en tractar la senyal.\n");
    }
    if(signal(SIGTERM, gestor_senyal) == SIG_ERR){  
        ImprimirError("Error en tractar la senyal.\n");
    }  
}

int ComprovarPrimer(int nombre)
{
    int i = 2;
    t_logic esPrimer = CERT;

    if (nombre > 2)
    {
        do
        {
            if (nombre % i == 0)
                esPrimer = FALS;

            i++;

        } while (i < nombre && esPrimer);
    }

    return((esPrimer == CERT ? 1 : 0 )); //Sí el nombre és primer retornem un 1, en cas contrari, 0
}


void ImprimirInfoControlador(char *text)
{
    unsigned char i;
    char info[numControlador * 3 + strlen(dadesControlador) + strlen(text) + 1];
    char infoColor[numControlador * 3 + strlen(dadesControlador) + strlen(text) + 1 + MIDA_MAX_CADENA_COLORS * 2];

    for (i = 0; i < numControlador * 3; i++)
        info[i] = ' ';

    for (i = 0; i < strlen(dadesControlador); i++)
        info[i + numControlador * 3] = dadesControlador[i];

    for (i = 0; i < strlen(text); i++)
        info[i + numControlador * 3 + strlen(dadesControlador)] = text[i];

    info[numControlador * 3 + strlen(dadesControlador) + strlen(text)] = '\0';

    sprintf(infoColor, "%s%s%s", taulaColors[(numControlador - 1) % 8], info, FI_COLOR);

    if (write(STDOUT_FILENO, infoColor, strlen(infoColor)) == -1)
        ImprimirError("ERROR write ImprimirInfoControlador");
}

void ImprimirError(char *text)
{
    unsigned char i;
    char info[numControlador * 3 + strlen(dadesControlador) + strlen(text) + 1];
    char infoColorError[MIDA_MAX_CADENA];

    for (i = 0; i < numControlador * 3; i++)
        info[i] = ' ';

    for (i = 0; i < strlen(dadesControlador); i++)
        info[i + numControlador * 3] = dadesControlador[i];

    for (i = 0; i < strlen(text); i++)
        info[i + numControlador * 3 + strlen(dadesControlador)] = text[i];

    info[numControlador * 3 + strlen(dadesControlador) + strlen(text)] = '\0';

    sprintf(infoColorError, "%s%s: %s%s\n", FORMAT_TEXT_ERROR, info, strerror(errno), FI_COLOR);
    write(STDERR_FILENO, "\n", 1);
    write(STDERR_FILENO, infoColorError, strlen(infoColorError));
    write(STDERR_FILENO, "\n", 1);

    exit(2);
}