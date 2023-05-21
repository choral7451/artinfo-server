import {Injectable} from "@nestjs/common";
import {PrismaService} from "@/apis/integration/prisma.service";
import {CreateCommentFields} from "@/apis/comment/type/create-comment.fields";
import {Comment, User} from "@prisma/client";
import {CommentsFilter} from "@/apis/comment/type/comments.filter";
import {CountCommentsFilter} from "@/apis/comment/type/count-comments.filter";

@Injectable()
export class CommentService {
  constructor(
    private readonly prismaService: PrismaService
  ) {}

  async countComments(filter:CountCommentsFilter): Promise<number> {
    const query: any = {};
    if(filter.postId) query.postId = filter.postId
    if(filter.parentCommentId) query.postId = filter.parentCommentId

    return this.prismaService.comment.count({
      where: query,
    });
  }

  async getComments(filter: CommentsFilter): Promise<(Comment & { user: User })[]> {

    const query: any = {};
    const sort: any =  { id: 'desc' }

    if (filter.lastItemId) {
      const lastItem = await this.prismaService.comment.findUnique({
        where: {
          id: filter.lastItemId,
        },
      });

      if (lastItem) {
        query.id = {
          lt: lastItem.id,
        };
      }
    }

    if(filter.postId) {
      query.postId = filter.postId
      query.parentCommentId = null
    }

    if(filter.parentCommentId) {
      query.parentCommentId = filter.parentCommentId
      sort.createdAt = 'asc'
    }

    return this.prismaService.comment.findMany({
      take: filter.countOfItems,
      include: { user: true },
      where: query,
      orderBy: sort
    })
  }

  async createComment(fields:CreateCommentFields):Promise<string> {
    const comment = await this.prismaService.comment.create({
      data: fields
    })

    return comment.id
  }

}