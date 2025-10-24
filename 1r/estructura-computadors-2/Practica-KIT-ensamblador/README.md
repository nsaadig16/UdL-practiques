# HundirLaFlotaKIT
## Apunts 26/04

>- **Direcció de control del teclat = 1 després de el teclat (INC)**
>- **Direcció pantalla: Si la pantalla està a R3, llavors li sumarem 78h (120 d) per obtindre la adreça del contol**
>- **CMP 2 REGISTRES: IGUAL = 0; DIFERENT = 1**
>- **A teclat fer servir tot el codi ASCII, a pantalla els dos ultims**

> CALL = crida \
> RET = fi de crida

> Moure una etiqueta a un registre (bytea/b direccion)
>


Fer funció amb push/pop
```
pintar pantalla:

    PUSH R0
    PUSH R1

    (funció)

    POP R1
    POP R0
RET

```