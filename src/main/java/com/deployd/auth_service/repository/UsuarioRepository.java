package com.deployd.auth_service.repository;

import com.deployd.auth_service.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
    Optional<Usuario> findByUserName(String username);


    Optional<Usuario> findById(Long id);

    // Caso de creación: busca por dni o username sin excluir

    // Caso de edición: excluye un usuario específico
   /* @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE (u.dni = :value OR u.userName = :value) AND u.id != :excludeUserId")
    boolean existsByDniOrUsername(@Param("value") String value, @Param("excludeUserId") Long excludeUserId);
*/
    boolean existsByDni(String dni);
    boolean existsByUserName(String userName);
    @Modifying
    @Query(value = "DELETE FROM user_local WHERE user_id = :userId", nativeQuery = true)
    void deleteUserLocalAssociations(@Param("userId") Integer userId);


    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.dni = :dni AND u.id != :excludeUserId")
    boolean existsByDniExcludingId(@Param("dni") String dni, @Param("excludeUserId") Long excludeUserId);

    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.userName = :userName AND (:excludeUserId IS NULL OR u.id != :excludeUserId)")
    boolean existsByUserNameExcludingId(@Param("userName") String userName, @Param("excludeUserId") Long excludeUserId);


}
