import {Module} from "@nestjs/common";
import {CommentResolver} from "@/apis/comment/comment.resolver";
import {CommentService} from "@/apis/comment/comment.service";
import {PrismaService} from "@/apis/integration/prisma.service";
import {UserService} from "@/apis/user/user.service";

@Module({
  providers:[CommentResolver, PrismaService, UserService, CommentService]
})
export class CommentModule {}