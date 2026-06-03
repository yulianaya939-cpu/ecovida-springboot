package com.ecovidasas.service;

import com.ecovidasas.entity.Residuo;
import com.ecovidasas.repository.ResiduoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResiduoService {

    @Autowired
    private ResiduoRepository residuoRepository;

    public List<Residuo> listarResiduos() {
        return residuoRepository.findAll();
    }

    public Residuo obtenerResiduoPorId(Long id) {
        return residuoRepository.findById(id).orElse(null);
    }

    public Residuo guardarResiduo(Residuo residuo) {
        return residuoRepository.save(residuo);
    }

    public void eliminarResiduo(Long id) {
        residuoRepository.deleteById(id);
    }
}