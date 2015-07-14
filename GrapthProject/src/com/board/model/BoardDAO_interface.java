package com.board.model;


import java.util.List;

public interface BoardDAO_interface {
	public void insert(BoardVO boardVO);
	public void update(BoardVO boardVO);
	public void delete(Integer b_id);
	public BoardVO findByPrimaryKey(Integer b_id);
	public List<BoardVO> getAll();
}
