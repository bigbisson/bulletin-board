package com.indran.bulletin_board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.indran.bulletin_board.model.Post;
import com.indran.bulletin_board.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class PostController {
    
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Display list of all active (isDeleted: false) posts
     * @param model
     * @return 
     */
    @GetMapping("/")
    public String listPosts(Model model) {
        logger.info("Displaying post list");
        List<Post> posts = postService.findAllActive();
        
        model.addAttribute("posts", posts);
        model.addAttribute("postCount", postService.countAllActive());
        return "list";
    }

    /**
     * Displaying single post (increments view count)
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        logger.info("Viewing post: {}", id);

        Post post = postService.findById(id);
        if (post == null) {
            logger.warn("Post not found: {}", id);
            redirectAttributes.addFlashAttribute("error", "Post not found");
            return "redirect:/";
        }
        postService.incrementViewCount(id);

        model.addAttribute("post", post);
        return "view";
    }

    /**
     * Go to create new post form
     * @param model
     * @return
     */
    @GetMapping("/post/new")
    public String createPostForm(Model model) {
        logger.info("Access form creat post");
        model.addAttribute("post", new Post());
        model.addAttribute("isEdit", false);
        return "form";
    }
    
    
    /**
     * Create new post
     * @param post
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/post")
    public String createPost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
        logger.info("Creating new post: {}", post.getTitle());

        try {
            postService.save(post);
            redirectAttributes.addFlashAttribute("succes", "Post created successfully");
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error creating post", e);
            redirectAttributes.addFlashAttribute("error", "Error creating post");
            return "redirect:/post/new";
        }
    }
    
    /**
     * Update/edit post (requires password)
     * TODO: hashing password
     * @param id
     * @param post
     * @param password
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/post/{id}/edit")
    public String updatePost(@PathVariable Long id, @ModelAttribute Post post, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        logger.info("Updating post: {}", id);

        post.setId(id);
        post.setPassword(password);

        boolean succes = postService.update(post);

        if (succes) {
            redirectAttributes.addFlashAttribute("success", "Post updated successfully");
            return "redirect:/post/" + id;
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid password or update failed");
            return "redirect:/post/" + id + "/edit?password=" + password; // TODO: change this for not using password on client side
        }
    }
    
    /**
     * Soft delete post (requires password)
     * TODO: hashing password
     * @param id
     * @param password
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/post{id}/delete")
    public String deletePost(@PathVariable Long id, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        logger.info("Deleting post: {}");

        boolean deleted = postService.softDelete(id, password);

        if (deleted) {
            redirectAttributes.addFlashAttribute("success", "Post deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid password");
        }
        
        return "redirect:/";
    }
    
    /**
     * Error page
     * @param message
     * @param model
     * @return
     */
    @GetMapping("/error")
    public String error(@RequestParam(name = "message", required = false) String message, Model model) {
        model.addAttribute("error", message != null ? message : "An error occurred");
        return "error";
    }
    
}
