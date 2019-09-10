package edu.pes.laresistencia.controller

import org.springframework.security.core.context.SecurityContextHolder


abstract class BaseController {

    protected fun getCurrentUser(): String {
        val auth = SecurityContextHolder.getContext().authentication
        return auth.name
    }
}