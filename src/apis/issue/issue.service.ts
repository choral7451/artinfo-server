import {Injectable} from "@nestjs/common";
import {PrismaService} from "@/apis/integration/prisma.service";
import {CreateIssueFields} from "@/apis/issue/type/create-issue.fields";
import {Issue, User} from "@prisma/client";
import {IssuesFilter} from "@/apis/issue/type/issues.filter";
import {CountIssuesFilter} from "@/apis/issue/type/count-issues.filter";

@Injectable()
export class IssueService {
  constructor(
    private readonly prismaService: PrismaService
  ) {}

  async createIssue(fields:CreateIssueFields):Promise<string> {
    const issue = await this.prismaService.issue.create({
      data: fields
    })

    return issue.id
  }

  async getIssueById(id: string): Promise<Issue & { user: User }> {
    const issue =  await this.prismaService.issue.findFirst({
      where: { id },
      include: { user: true }
    })

    if(!issue) throw new Error('THE_POST_DOES_NOT_EXIST')

    return issue
  }

  async countIssues(filter:CountIssuesFilter): Promise<number> {
    const query: any = {};
    if(filter.type) query.type = filter.type
    if (filter.keyword) {
      query.OR = [
        { title: { contains: filter.keyword } },
        { contents: { contains: filter.keyword } },
      ];
    }

    return this.prismaService.issue.count({
      where: query,
    });
  }

  async getIssues(filter: IssuesFilter): Promise<(Issue & { user: User })[]> {
    const query: any = {};
    if (filter.lastItemId) {
      const lastItem = await this.prismaService.issue.findUnique({
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

    if(filter.type) query.type = filter.type
    if (filter.keyword) {
      query.title  = { contains: filter.keyword }
    }

    return this.prismaService.issue.findMany({
      take: filter.countOfItems,
      include: {user: true},
      where: query,
      orderBy: { id: 'desc' }
    });
  }
}