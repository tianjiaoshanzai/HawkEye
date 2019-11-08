package edu.hfut.hawk_eye.controller;

import edu.hfut.hawk_eye.bean.JsonResult;
import edu.hfut.hawk_eye.entity.News;
import edu.hfut.hawk_eye.entity.User;
import edu.hfut.hawk_eye.service.NewsService;
import edu.hfut.hawk_eye.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yxs
 */
@RestController
@RequestMapping("/users")
public class UserController {

    public final static String SESSION_KEY = "user";

    @Autowired
    UserService userService;

    /**
     * 添加用户
     * @param user
     * @return
     */
    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping("/")
    public ResponseEntity<JsonResult<User>> add (@RequestBody User user){
        JsonResult<User> r = new JsonResult<>();


        if (userService.add(user)) {
            return ResponseEntity.ok(r);
        } else {
            return ResponseEntity.ok(new JsonResult<User>("创建用户失败"));
        }
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value="删除用户", notes="根据id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResult> delete (@PathVariable(value = "id") Integer id){
        JsonResult r = new JsonResult();
        r.setMessage(id.toString());

        if (userService.delete(id)) {
            return ResponseEntity.ok(r);
        } else {
            return ResponseEntity.ok(new JsonResult<User>("删除用户失败"));
        }

    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @ApiOperation(value="查询用户", notes="根据id来指定查询用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/{id}")
    public ResponseEntity<JsonResult> getUser (@PathVariable(value = "id") Integer id){

        return ResponseEntity.ok(new JsonResult<>(userService.getUserById(id)));


    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @ApiOperation(value="注销", notes="注销")
    @PostMapping("/logout")
    public ResponseEntity<JsonResult> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        JsonResult r = new JsonResult();
        r.setMessage("ok");
        return ResponseEntity.ok(r);
    }

    @ApiOperation(value="登录", notes="登录")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping("/login")
    public ResponseEntity<JsonResult> login(HttpServletRequest request, @RequestBody User user) {
        User user1 = userService.login(user.getUserName(), user.getPassword());
        if (user1 == null) {
            return ResponseEntity.ok(new JsonResult<User>("密码错误"));
        }

        request.getSession().setAttribute(SESSION_KEY,user1.getId());
        JsonResult r = new JsonResult();
        r.setMessage("ok");
        return ResponseEntity.ok(r);
    }


    /**
     *更改密码
     * @param id
     * @param user
     * @return
     */
    @ApiOperation(value="更改密码", notes="更改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户 ID", required = true, dataType = "Long",paramType = "path"),
            @ApiImplicitParam(name = "user", value = "用户实体 user", required = true, dataType = "User")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<JsonResult> update (@PathVariable("id") Integer id, @RequestBody User user){
        user.setId(id);
        JsonResult r = new JsonResult();
        r.setMessage(id + "");
        r.setData(user);

        if (userService.updatePwd(id,user.getPassword())) {
            return ResponseEntity.ok(r);
        } else {
            return ResponseEntity.ok(new JsonResult<User>("更改密码失败"));
        }

    }

    /**
     * 获取登录用户信息
     * @return
     */
    @ApiOperation(value="获取登录用户信息", notes="获取登录用户信息")
    @GetMapping("/get")
    public ResponseEntity<JsonResult> get (HttpServletRequest request){
        Integer uid = (Integer) request.getSession().getAttribute(SESSION_KEY);

        if (uid == null) {
            return ResponseEntity.ok(new JsonResult<User>("用户未登录"));
        }


        return ResponseEntity.ok(new JsonResult<>(userService.getUserById(uid)));
    }

}
