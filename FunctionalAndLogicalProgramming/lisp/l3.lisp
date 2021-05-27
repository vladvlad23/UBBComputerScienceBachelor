(defun flatten (givenParameter)
    (cond
        ((null givenParameter) nil)
        ((atom (car givenParameter)) (append (list (car givenParameter)) (flatten (cdr givenParameter))))
        ((append (flatten (car givenParameter)) (flatten (cdr givenParameter))))
    )

)

(defun contains (givenList givenAtom)
    (cond
        ((atom givenList) (eq givenList givenAtom))
        ((not (every #'null (flatten (mapcar #'(lambda (b) (contains b givenAtom)) givenList)))) t)
    )
)