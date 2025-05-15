
/* -----------------------------------------------------------------------
 PRA1. Processos, pipes i senyals: Primers
 Codi font: generador.c 
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
#define PIPE_ESCRIPTURA_NOMBRES 10 //Descriptors dels fitxers dels pipes

/*
----------------------------------------------------------------------
--- C A P Ç A L E R E S   D E   F U N C T I O N S --------------------
----------------------------------------------------------------------
*/
void ImprimirError(char *text);
void tractament_senyals();
void gestor_senyal(int sig);

/*
----------------------------------------------------------------------
--- V A R I A B L E S   G L O B A L S --------------------------------
----------------------------------------------------------------------
*/
unsigned char numControlador;
pid_t pidPropi;
char cadena[MIDA_MAX_CADENA];
char dadesControlador[MIDA_MAX_CADENA];
int M;  //Número fins al que ha de generar.
int esperantSIGTERM = 1;    //Variables que es mantenen certes mentres esperem a les senyals
int esperantSIGQUIT = 1;

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

int main(int argc, char *argv[]){

    if(argc != 2){          
        sprintf(cadena, "Us: %s <darrer nombre enter per a la seqüència 2 a M> \n\nPer exemple: %s 11\n\n", argv[0], argv[0]);
        write(1, cadena, strlen(cadena));
        exit(2);
    }

    M = atoi(argv[1]);  //Transformem la M a enter

    if(M < 2){
        ImprimirError("El número passat com a paràmetre M no és vàlid.\n");
    }

    tractament_senyals();   //Tractem les senyals

    sprintf(cadena,"[./generador-pid:%u]>Activat i esperant la senyal SIGQUIT (Ctrl+4 al teclat) per a generar la seqüència 2 a %i.\n",getpid(), M);
    write(STDOUT_FILENO, cadena, strlen(cadena));   //Envia el missatge d'espera

    while(esperantSIGQUIT){ //Esperem la senyal SIGQUIT
        pause();
    }
  
    for(int i = 2; i <= M; i++){
        if(write(PIPE_ESCRIPTURA_NOMBRES, &i, sizeof(int)) == -1){  //Escrivim al pipe de nombres els nombres del 2 a M
            sprintf(cadena,"Error en l'escriptura al pipe de nombres.\n");
            ImprimirError(cadena);
        }
    }
    close(PIPE_ESCRIPTURA_NOMBRES); //Tanquem el pipe al acabar

    while(esperantSIGTERM){ //Esperem la senyal SIGTERM
        pause();
    }
   
    exit(EXIT_SUCCESS); //Fem l'exit 
}

void tractament_senyals(){  //Tractem amb un gestor SIGQUIT i SIGTERM i ignorem SIGINT
    if(signal(SIGQUIT, gestor_senyal) == SIG_ERR){
        ImprimirError("Error en tractar la senyal.\n");
    }
    if(signal(SIGTERM, gestor_senyal) == SIG_ERR){  
        ImprimirError("Error en tractar la senyal.\n");
    }
    if(signal(SIGINT, SIG_IGN) == SIG_ERR){
        ImprimirError("Error en tractar la senyal.\n");
    }
}

void gestor_senyal(int sig){    //Depenent de la senyal que rebem, fiquem la variable corresponent a 0 per sortir del bucle
    if(sig == SIGQUIT){
        esperantSIGQUIT = 0;
    }
    if(sig == SIGTERM){
        esperantSIGTERM = 0;
    }
}

void ImprimirError(char *text){
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