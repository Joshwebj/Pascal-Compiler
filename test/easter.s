# Code file created by Pascal2100 compiler 2015-11-16 13:50:11
        .extern write_char                         
        .extern write_int                         
        .extern write_string                         
        .globl _main                         
        .globl main                         
_main:                                  
main:   call    prog$easter_1           # Start program
        movl    $0,%eax                 # Set status 0 and
        ret                             # terminate the program
proc$easter_2:                                
        enter   $92,$2                  # Start of easter
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  var y
        pushl   %eax                    
        movl    $19,%eax                #  int 19
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        movl    -8(%ebp),%edx           
        movl    %eax,-36(%edx)          # a :=
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  var y
        pushl   %eax                    
        movl    $100,%eax               #  int 100
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-40(%edx)          # b :=
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  var y
        pushl   %eax                    
        movl    $100,%eax               #  int 100
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        movl    -8(%ebp),%edx           
        movl    %eax,-44(%edx)          # c :=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  var b
        pushl   %eax                    
        movl    $4,%eax                 #  int 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-48(%edx)          # d :=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  var b
        pushl   %eax                    
        movl    $4,%eax                 #  int 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        movl    -8(%ebp),%edx           
        movl    %eax,-52(%edx)          # e :=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  var b
        pushl   %eax                    
        movl    $8,%eax                 #  int 8
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $25,%eax                #  int 25
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-56(%edx)          # f :=
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  var b
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -56(%edx),%eax          #  var f
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #  -
        pushl   %eax                    
        movl    $1,%eax                 #  int 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $3,%eax                 #  int 3
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-60(%edx)          # g :=
        movl    $19,%eax                #  int 19
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #  var a
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #  *
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -40(%edx),%eax          #  var b
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -48(%edx),%eax          #  var d
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #  -
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -60(%edx),%eax          #  var g
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #  -
        pushl   %eax                    
        movl    $15,%eax                #  int 15
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $30,%eax                #  int 30
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        movl    -8(%ebp),%edx           
        movl    %eax,-64(%edx)          # h :=
        movl    -8(%ebp),%edx           
        movl    -44(%edx),%eax          #  var c
        pushl   %eax                    
        movl    $4,%eax                 #  int 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-68(%edx)          # i :=
        movl    -8(%ebp),%edx           
        movl    -44(%edx),%eax          #  var c
        pushl   %eax                    
        movl    $4,%eax                 #  int 4
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        movl    -8(%ebp),%edx           
        movl    %eax,-72(%edx)          # k :=
        movl    $32,%eax                #  int 32
        pushl   %eax                    
        movl    $2,%eax                 #  int 2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -52(%edx),%eax          #  var e
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #  *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $2,%eax                 #  int 2
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -68(%edx),%eax          #  var i
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #  *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  var h
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #  -
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -72(%edx),%eax          #  var k
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #  -
        pushl   %eax                    
        movl    $7,%eax                 #  int 7
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        movl    -8(%ebp),%edx           
        movl    %eax,-76(%edx)          # l :=
        movl    -8(%ebp),%edx           
        movl    -36(%edx),%eax          #  var a
        pushl   %eax                    
        movl    $11,%eax                #  int 11
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  var h
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #  *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $22,%eax                #  int 22
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  var l
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #  *
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $451,%eax               #  int 451
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-80(%edx)          # m :=
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  var h
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  var l
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $7,%eax                 #  int 7
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -80(%edx),%eax          #  var m
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #  *
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #  -
        pushl   %eax                    
        movl    $114,%eax               #  int 114
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $31,%eax                #  int 31
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    #  /
        movl    -8(%ebp),%edx           
        movl    %eax,-84(%edx)          # month :=
        movl    -8(%ebp),%edx           
        movl    -64(%edx),%eax          #  var h
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -76(%edx),%eax          #  var l
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $7,%eax                 #  int 7
        pushl   %eax                    
        movl    -8(%ebp),%edx           
        movl    -80(%edx),%eax          #  var m
        movl    %eax,%ecx               
        popl    %eax                    
        imull   %ecx,%eax               #  *
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               #  -
        pushl   %eax                    
        movl    $114,%eax               #  int 114
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        pushl   %eax                    
        movl    $31,%eax                #  int 31
        movl    %eax,%ecx               
        popl    %eax                    
        cdq                             
        idivl   %ecx                    
        movl    %edx,%eax               #  mod
        pushl   %eax                    
        movl    $1,%eax                 #  int 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        movl    -8(%ebp),%edx           
        movl    %eax,-88(%edx)          # day :=
                                        # Start if-statement
        movl    -8(%ebp),%edx           
        movl    -84(%edx),%eax          #  var month
        pushl   %eax                    
        movl    $3,%eax                 #  int 3
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        sete    %al                     # Test ==
        cmpl    $0,%eax                 
        je      .L0003                  
        movl    -8(%ebp),%edx           
        movl    -88(%edx),%eax          #  var day
        pushl   %eax                    # Push param #1.
        call    write_int               
        addl    $4,%esp                 # Pop parameter.
        .data                  
.L0005: .asciz   " March "
        .align  2              
        .text                  
        leal    .L0005,%eax             # Addr(" March ")
        pushl   %eax                    # Push param #2.
        call    write_string            
        addl    $4,%esp                 # Pop parameter.
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  var y
        pushl   %eax                    # Push param #3.
        call    write_int               
        addl    $4,%esp                 # Pop parameter.
        movl    $10,%eax                #  char 10
        pushl   %eax                    # Push param #4.
        call    write_char              
        addl    $4,%esp                 # Pop parameter.
        jmp     .L0004                  
.L0003:                                 
        movl    -8(%ebp),%edx           
        movl    -88(%edx),%eax          #  var day
        pushl   %eax                    # Push param #1.
        call    write_int               
        addl    $4,%esp                 # Pop parameter.
        .data                  
.L0006: .asciz   " April "
        .align  2              
        .text                  
        leal    .L0006,%eax             # Addr(" April ")
        pushl   %eax                    # Push param #2.
        call    write_string            
        addl    $4,%esp                 # Pop parameter.
        movl    -8(%ebp),%edx           
        movl    8(%edx),%eax            #  var y
        pushl   %eax                    # Push param #3.
        call    write_int               
        addl    $4,%esp                 # Pop parameter.
        movl    $10,%eax                #  char 10
        pushl   %eax                    # Push param #4.
        call    write_char              
        addl    $4,%esp                 # Pop parameter.
.L0004:                                 # End if-statement
        leave                           # End of easter
        ret                             
prog$easter_1:                                
        enter   $36,$1                  # Start of easter
        movl    $2010,%eax              #  int 2010
        movl    -4(%ebp),%edx           
        movl    %eax,-36(%edx)          # y :=
.L0007:                                 # Start while-statement
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  var y
        pushl   %eax                    
        movl    $2020,%eax              #  int 2020
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setle   %al                     # Test <=
        cmpl    $0,%eax                 
        je      .L0008                  
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  var y
        pushl   %eax                    # Push param #1
        call    proc$easter_2           
        addl    $4,%esp                 # Pop parameters.
        movl    -4(%ebp),%edx           
        movl    -36(%edx),%eax          #  var y
        pushl   %eax                    
        movl    $1,%eax                 #  int 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               #  +
        movl    -4(%ebp),%edx           
        movl    %eax,-36(%edx)          # y :=
        jmp     .L0007                  
.L0008:                                 # End while-statement
        leave                           # End of easter
        ret                             
