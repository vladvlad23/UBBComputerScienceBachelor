; Write a program in the assembly language that computes the following arithmetic expression, considering the following data types for the variables:
; a - byte, b - word
; (b / a * 2 + 10) * b - b * 15;
; ex. 1: a = 10; b = 40; Result: (40 / 10 * 2 + 10) * 40 - 40 * 15 = 18 * 40 - 1600 = 720 - 600 = 120
; ex. 2: a = 100; b = 50; Result: (50 / 100 * 2 + 10) * 50 - 50 * 15 = 12 * 50 - 750 = 600 - 750 = - 150

bits 32

global start

extern exit 

import exit msvcrt.dll

segment data use=32 class=data
    a db 100
    b dw 50
    
segment data use=32 class=code
start:
    mov AX, [b] ; AL = b
    mov BL, [a] ; BL = a
    div BL ; AL = AX/BL, AH=AX%BL <=> AL = b/a && AH = b%a
    mov BL, 2
    mul BL ; AL = AL*BL <=> AX = b/a*2
    add AX, 10 ; AX = (b/a*2 +10)
    mov BX, [b] ; BL=b
    mul BX ; AL = AX/BL, AH = AX%BL <=> AL = (b/a*2+10)*b
    push DX ; push ax to stack
    push AX 
    pop EBX
    mov AX, [b]
    mov DX, 15
    mul DX ; DX:AX = AX*DX = b*15
    push DX ; dx to stack
    push AX ; ax to stack
    pop EAX ; EAX = DX:AX = b*15
    sub EBX,EAX ; (b/a*2+10)*b-b*15
    push dword[0]
    call [exit]

    ;DOAMNE AJUTA