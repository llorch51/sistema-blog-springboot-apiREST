package com.sistema.blog.repository;

import com.sistema.blog.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {


}
