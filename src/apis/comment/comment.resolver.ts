import {Args, Mutation, Query, Resolver} from "@nestjs/graphql";
import {CommentService} from "@/apis/comment/comment.service";
import {CreateCommentRequest} from "@/apis/comment/dto/request/create-comment.request";
import {CreateResponse} from "@/apis/integration/dto/create.response";
import {Signature} from "@/commons/decorator/signature";
import {UseGuards} from "@nestjs/common";
import {JwtAuthGuard} from "@/commons/auth/jwt-auth.guard";
import {CommentsResponse} from "@/apis/comment/dto/response/comments.response";
import {CommentsRequest} from "@/apis/comment/dto/request/comments.request";

@Resolver()
export class CommentResolver {

  constructor(
    private readonly commentService: CommentService
  ) {}

  @Query(() => CommentsResponse)
  async getComments(@Args('input') request: CommentsRequest) {
    const comments = await this.commentService.getComments(request)
    const countOfTotal = await this.commentService.countComments(request)

    return CommentsResponse.fromComments(comments, countOfTotal)
  }

  @UseGuards(JwtAuthGuard)
  @Mutation(() => CreateResponse)
  async createComment(@Args('input') request: CreateCommentRequest, @Signature() signature) {
    const commentId = await this.commentService.createComment(request.setUserId(signature.id))
    return CreateResponse.fromId(commentId)
  }
}