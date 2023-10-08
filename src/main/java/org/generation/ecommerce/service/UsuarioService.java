package org.generation.ecommerce.service;


import java.util.List;
import java.util.Optional;

import org.generation.ecommerce.model.ChangePassword;
import org.generation.ecommerce.model.Usuario;
import org.generation.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	public List<Usuario> getAllUsuarios() {
		return usuarioRepository.findAll();
	}

	public Usuario getUsuario(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				()-> new IllegalArgumentException("El producto con el id ["
						+ id + "] no existe")
				);
	}
	public Usuario deleteUsuario(Long id) {
		Usuario tmp = null;
		if(usuarioRepository.existsById(id)) {
			tmp = usuarioRepository.findById(id).get();
			usuarioRepository.deleteById(id);
		}
		return tmp;
	}
	
	public Usuario addUsuario(Usuario usuario) {
		Usuario tmp = null;
		if(usuarioRepository.findByEmail(usuario.getEmail()).isEmpty()) {
			
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			tmp = usuarioRepository.save(usuario);
		}
		else {
			System.out.println("Ya existe el producto con el email ["
					+ usuario.getEmail() + "] ya se encuentra registrado");
		}
		return tmp;
	}
	
	
	public Usuario updateUsuario(Long id, ChangePassword changePassword) {
		Usuario tmp = null;
			if(usuarioRepository.existsById(id)) {
				tmp = usuarioRepository.findById(id).get();
				if(changePassword.getPassword()!=null
						&& changePassword.getNewPassword()!=null) {
					//if(tmp.getPassword().equals(changePassword.getPassword())){
					if(passwordEncoder.matches(changePassword.getPassword(), tmp.getPassword())) {
						tmp.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
						usuarioRepository.save(tmp);
					}else {
							tmp=null;
						}//else  //if password
					}//if !=null
				}else {
					System.out.println("Update - El usuario con el id [" + id
							+ "] no existe");
			}
		return tmp;
	}

	public boolean validateUsuario(Usuario usuario) {
		Optional<Usuario> userByEmail =
				usuarioRepository.findByEmail(usuario.getEmail());
		if(userByEmail.isPresent()) {
			Usuario user = userByEmail.get();
			if(passwordEncoder.matches(usuario.getPassword(),user.getPassword())) {
				return true;
			}
		}
		return false;
	}
}