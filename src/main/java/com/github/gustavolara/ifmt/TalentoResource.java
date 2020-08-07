package com.github.gustavolara.ifmt;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/talentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TalentoResource {

    @Inject
    private TalentoService talentoService;

    @GET
    public List<Talento> getAllTalentos() {
        return talentoService.findAll();
    }

    @GET
    @Path("{id}")
    public Talento getTalento(@PathParam("id") Integer id) {
        Optional<Talento> optionalTalento = talentoService.findById(id);
        return optionalTalento.orElseThrow(NotFoundException::new);
    }

    @POST
    public Talento addTalento(TalentoDTO talentoDTO){
        Talento talento = new Talento();
        talento.setNome(talentoDTO.getNome());
        talento.setEmail(talentoDTO.getEmail());
        talento.setAreaDeAtuacao(talentoDTO.getAreaDeAtuacao());
        talento.setHabilidades(talentoDTO.getHabilidades());
        talento.setApresentacao(talentoDTO.getApresentacao());
        return talentoService.saveTalento(talento);
    }

    @PUT
    @Path("{id}")
    public Talento updateTalento(@PathParam("id") Integer id, TalentoDTO talentoDTO){
        Optional<Talento> talentoOptional = talentoService.findById(id);
        Talento talento;
        if(talentoOptional.isPresent()){
            talento = talentoOptional.get();
            talento.setNome(talentoDTO.getNome());
            talento.setAreaDeAtuacao(talentoDTO.getAreaDeAtuacao());
            talento.setHabilidades(talentoDTO.getHabilidades());
            talento.setApresentacao(talentoDTO.getApresentacao());
        } else {
            throw new NotFoundException();
        }
        return talentoService.saveTalento(talento);
    }

    @DELETE
    @Path("{id}")
    public void deleteTalento(@PathParam("id") Integer id){
        talentoService.deleteTalento(id);
    }
}
