package ru.nsu.fit.g17205.kondratev.DBApp.controller;



import ru.nsu.fit.g17205.kondratev.DBApp.service.NodeService;
import ru.nsu.fit.g17205.kondratev.DBApp.dto.NodeDTO;
import ru.nsu.fit.g17205.kondratev.DBApp.dto.SearchDTO;
import ru.nsu.fit.g17205.kondratev.DBApp.model.NodeEntity;
import ru.nsu.fit.g17205.kondratev.DBApp.service.OSMReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    OSMReaderService osmReader;
    @Autowired
    NodeService nodeProcessor;

    @GetMapping("/{id}")
    public ResponseEntity<NodeDTO> getNode(@PathVariable("id") Long id){
        NodeEntity nodeEntity = nodeProcessor.getNode(id);
        if (nodeEntity != null){
            return new ResponseEntity<>(NodeDTO.convert(nodeEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        nodeProcessor.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NodeDTO> updateNode(@PathVariable("id") Long id,
                       @Valid @RequestBody NodeDTO node) {
        try {
            NodeEntity nodeEntity = nodeProcessor.update(id, NodeEntity.convert(node));
            return new ResponseEntity<>(NodeDTO.convert(nodeEntity), HttpStatus.OK);
        } catch (NullPointerException e)
         {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/save")
    public ResponseEntity<NodeDTO> saveNode(@Valid @RequestBody NodeDTO nodeDTO){
        return new ResponseEntity<>(NodeDTO.convert(nodeProcessor.save(NodeEntity.convert(nodeDTO))), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<NodeDTO>> searchNodes(@Valid @RequestBody SearchDTO searchDTO){
        return new ResponseEntity<>(
                nodeProcessor.findNodesInRadius(searchDTO.getLatitude(), searchDTO.getLongitude(), searchDTO.getRadius()).stream()
                .map(NodeDTO::convert)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/readOSM")
    public void readOSM() throws Exception{
        osmReader.readFile();
    }
}
